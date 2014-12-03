/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
define(['knockout',
        'timeselector/time-selector-model',  
        'ojs/ojcore',
        'jquery',
        'jqueryui',
        'ojs/ojknockout',
        'ojs/ojmenu',
        'html2canvas',
        'canvg-rgbcolor',
        'canvg-stackblur',
        'canvg'
    ],
    
    function(ko, TimeSelectorModel)
    {
        function guid() {
            function S4() {
               return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
            }
            return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
        }
        
        /**
         * 
         * @param {ko.observable} startTime: start time of new time range
         * @param {ko.observable} endTime: end time of new time range
         * @returns {DashboardTimeRangeChange} instance
         */
        function DashboardTimeRangeChange(startTime, endTime){
            var self = this;
            if (ko.isObservable(startTime) && startTime() instanceof Date){
                self.viewStartTime = startTime;
            }
            if (ko.isObservable(endTime) && endTime() instanceof Date){
                self.viewEndTime = endTime;
            }
        }
        
        /**
         * 
         * @param {String} name: name of custome item
         * @param {Object} value: new value of custome item
         * @returns {undefined}
         */
        function DashboardCustomChange(name, value){
            var self = this;
            if (name){
                self.name = name.toString();
                self.value = value;
            }
        }

        function DashboardItemChangeEvent(timeRangeChange, customChanges){
            var self = this;
            self.timeRangeChange = null;
            self.customChanges = null;
            if (timeRangeChange instanceof DashboardTimeRangeChange){
                self.timeRangeChange = timeRangeChange;
            }

            if (customChanges instanceof Array){
                for(var i=0;i<customChanges.length;i++){
                    var change = customChanges[i];
                    if (change instanceof DashboardCustomChange){
                        if (!self.customChanges){
                            self.customChanges = [];
                        }
                        self.customChanges.push(change);
                    }else{
                        console.log("ERROR: "+"invalid custom change: "+change);
                    }
                }
            }
        }
        
        /* used for iFrame integration
        function DashboardTile(dashboard,type, title, description, width, url, chartType) {
            var self = this;
            self.dashboard = dashboard;
            self.type = type;
            self.title = ko.observable(title);
            self.description = ko.observable(description);
            self.url = ko.observable(url);
            self.chartType = ko.observable(chartType);
            self.maximized = ko.observable(false);
            
            self.fullUrl = ko.computed(function() {
                if (!self.chartType() || self.chartType() === "")
                    return self.url();
                return self.url() + "?chartType=" + self.chartType();
            });
            
            self.shouldHide = ko.observable(false);
            self.tileWidth = ko.observable(width);
            self.clientGuid = guid();
            self.widerEnabled = ko.computed(function() {
                return self.tileWidth() < 4;
            });
            self.narrowerEnabled = ko.computed(function() {
                return self.tileWidth() > 1;
            });
            self.maximizeEnabled = ko.computed(function() {
                return !self.maximized();
            });
            self.restoreEnabled = ko.computed(function() {
                return self.maximized();
            });
            self.tileDisplayClass = ko.computed(function() {
                var css = 'oj-md-'+(self.tileWidth()*3) + ' oj-sm-'+(self.tileWidth()*3) + ' oj-lg-'+(self.tileWidth()*3);
                css += self.maximized() ? ' dbd-tile-maximized' : ' ';
                css += self.shouldHide() ? ' dbd-tile-no-display' : ' ';
                return css;
            });

        }
         */


        /**
         *  used for KOC integration
         */
        function DashboardWidget(dashboard,type, title, description, width, widget) {
            var self = this;
            self.dashboard = dashboard;
            self.type = type;
            self.title = ko.observable(title);
            self.description = ko.observable(description);
            self.maximized = ko.observable(false);
            self.shouldHide = ko.observable(false);
            self.tileWidth = ko.observable(width);
            self.clientGuid = guid();
            self.widerEnabled = ko.computed(function() {
                return self.tileWidth() < 4;
            });
            self.narrowerEnabled = ko.computed(function() {
                return self.tileWidth() > 1;
            });
            self.maximizeEnabled = ko.computed(function() {
                return !self.maximized();
            });
            self.restoreEnabled = ko.computed(function() {
                return self.maximized();
            });
            self.tileDisplayClass = ko.computed(function() {
                var css = 'oj-md-'+(self.tileWidth()*3) + ' oj-sm-'+(self.tileWidth()*3) + ' oj-lg-'+(self.tileWidth()*3);
                css += self.maximized() ? ' dbd-tile-maximized' : ' ';
                css += self.shouldHide() ? ' dbd-tile-no-display' : ' ';
                return css;
            });
            
            self.widget = widget;
    
            /**
             * Integrator needs to override below FUNCTION to respond to DashboardItemChangeEvent
             * e.g.
             * params.tile.onDashboardItemChangeEvent = function(dashboardItemChangeEvent) {...}
             * Note:
             * Integrator will get a parameter: params by which integrator can access tile related properties/method/function
             */
            self.onDashboardItemChangeEvent = null;
            
            self.fireDashboardItemChangeEvent = function(dashboardItemChangeEvent){
                self.dashboard.fireDashboardItemChangeEvent(dashboardItemChangeEvent);
            }
        }
        
        function DashboardTilesViewModel(tilesView, urlEditView, timeSliderModel, widgetsHomRef, dsbType) {
            var self = this;
            self.timeSelectorModel = new TimeSelectorModel();
            self.tilesView = tilesView;
            self.tileRemoveCallbacks = [];
            self.isOnePageType = (dsbType === "onePage");
            
            var widgets = [];
            if (self.isOnePageType) {
                var defaultWidgetTitle = (widgetsHomRef && widgetsHomRef.length > 0) ? widgetsHomRef[0].title : "Home";
                widgets.push(new DashboardWidget(self, widgetsHomRef[0]["WIDGET_KOC_NAME"], defaultWidgetTitle, "", 2,widgetsHomRef[0]));
            } else if (widgetsHomRef) {
                for (i = 0; i < widgetsHomRef.length; i++) {
                    var widget = new DashboardWidget(self, widgetsHomRef[i]["WIDGET_KOC_NAME"], widgetsHomRef[i].title, "", widgetsHomRef[i]["TILE_WIDTH"],widgetsHomRef[i]);
                    widgets.push(widget);
                }
            }

            self.tiles = ko.observableArray(widgets);
            
            self.disableTilesOperateMenu = ko.observable(self.isOnePageType);

            self.isEmpty = function() {
                return !self.tiles() || self.tiles().length === 0;
            };
            
            self.registerTileRemoveCallback = function(callbackMethod) {
                self.tileRemoveCallbacks.push(callbackMethod);
            };
            
            self.appendNewTile = function(name, description, width, widget) {
//                var newTile =new DashboardWidget(name, description, width, document.location.protocol + '//' + document.location.host + "/emcpdfui/dependencies/visualization/dataVisualization.html", charType);
                var newTile = null;
                //demo log analytics widget
                if (widget && widget.type === 1) {
                    //find KOC name for registration. if valid registration is not detected, use default one.
                    var href = widget.href;
                    var widgetDetails = null;
                    $.ajax({
                        url: href,
                        success: function(data, textStatus) {
                            widgetDetails = data;
                        },
                        error: function(xhr, textStatus, errorThrown){
                            console.log('Error when get widget details!');
                        },
                        async: false
                    });
                    var koc_name = null;
                    var template = null;
                    var viewmodel = null;
                    if (widgetDetails){
                        if (widgetDetails.parameters instanceof Array && widgetDetails.parameters.length>0){
                           for(var int=0;i<widgetDetails.parameters.length;i++){
                               if ("WIDGET_KOC_NAME"==widgetDetails.parameters[i]["name"]){
                                    koc_name = widgetDetails.parameters[i]["value"];
                               }else if ("WIDGET_TEMPLATE"==widgetDetails.parameters[i]["name"]){
                                   template = widgetDetails.parameters[i]["value"];
                               }else if ("WIDGET_VIEWMODEL"==widgetDetails.parameters[i]["name"]){
                                   viewmodel = widgetDetails.parameters[i]["value"];
                               }
                           }
                        }
                    }
                    if (koc_name && template && viewmodel){
                      ko.components.register(koc_name,{
                           viewModel:{require:viewmodel},
                           template:{require:'text!'+template}
                       }); 
                      console.log("widget: "+koc_name+" is registered");
                      console.log("widget template: "+template);
                      console.log("widget viewmodel:: "+viewmodel);
                      newTile =new DashboardWidget(self,koc_name,name, description, width, widget); 
                    }else{
                       newTile =new DashboardWidget(self,"demo-la-widget",name, description, width, widget); 
                    }
                    
                }
                //demo target analytics widget
                else if (widget && widget.type === 2) {
                    newTile =new DashboardWidget(self,"demo-ta-widget",name, description, width, widget);
                }
                //demo simple chart widget
                else {
                    newTile =new DashboardWidget(self,"demo-chart-widget",name, description, width, widget);
                }
                self.tiles.push(newTile);
            };

            self.removeTile = function(tile) {
                self.tiles.remove(tile);
                for (var i = 0; i < self.tiles().length; i++) {
                    var eachTile = self.tiles()[i];
                    eachTile.shouldHide(false);
                }
                self.tilesView.enableSortable();
                self.tilesView.enableDraggable();
                for (var i = 0; i < self.tileRemoveCallbacks.length; i++) {
                    self.tileRemoveCallbacks[i]();
                }
                
                self.postTileMenuClicked(tile);
            };
            
            self.broadenTile = function(tile) {
                if (tile.tileWidth() <= 3)
                    tile.tileWidth(tile.tileWidth() + 1);
                
                self.postTileMenuClicked(tile);
            };
            
            self.narrowTile = function(tile) {
                if (tile.tileWidth() > 1)
                    tile.tileWidth(tile.tileWidth() - 1);
                
                self.postTileMenuClicked(tile);
            };
            
            self.calculateTilesRowHeight = function() {
                var tilesRow = $('#tiles-row');
                var tilesRowSpace = parseInt(tilesRow.css('margin-top'), 0) 
                        + parseInt(tilesRow.css('margin-bottom'), 0) 
                        + parseInt(tilesRow.css('padding-top'), 0) 
                        + parseInt(tilesRow.css('padding-bottom'), 0);
                var tileSpace = parseInt($('.dbd-tile-maximized .dbd-tile-element').css('margin-bottom'), 0) 
                        + parseInt($('.dbd-tile-maximized').css('padding-bottom'), 0)
                        + parseInt($('.dbd-tile-maximized').css('padding-top'), 0);
                return $(window).height() - $('#headerWrapper').outerHeight() 
                        - $('#head-bar-container').outerHeight() - $('#global-time-slider').outerHeight() 
                        - (isNaN(tilesRowSpace) ? 0 : tilesRowSpace) - (isNaN(tileSpace) ? 0 : tileSpace);
            };
            
            // maximize 1st tile only, used for one-page type dashboard
            self.maximizeFirst = function() {
                if (self.isOnePageType && self.tiles() && self.tiles().length > 0) {
                    if (!$('#main-container').hasClass('dbd-one-page')) {
                        $('#main-container').addClass('dbd-one-page');
                    }
                    if (!$('#tiles-row').hasClass('dbd-one-page')) {
                        $('#tiles-row').addClass('dbd-one-page');
                    }
                    var tile = self.tiles()[0];
                    
                    var tileId = 'tile' + tile.clientGuid;
                    var iframe = $('#' + tileId + ' div iframe');
                    globalDom = iframe.context.body;
                    var height = globalDom.scrollHeight;
                    var maximizedTileHeight = self.calculateTilesRowHeight();
                    height = (maximizedTileHeight > height) ? maximizedTileHeight : height;
                    originalHeight = height;
                    var width = globalDom.scrollWidth;
                    console.log('scroll width for iframe inside one page dashboard is ' + width + 'px');
                    //waitForIFrameLoading();
                    // following are investigation code, and now work actually for plugins loaded by requireJS
//                    $($('#df_iframe').context).ready(function() {
//                        alert('iframe loaded');
//                    });
//                    $("iframe").on("iframeloading iframeready iframeloaded iframebeforeunload iframeunloaded", function(e){
//                        console.log(e.type);
//                    });
//                    requirejs.onResourceLoad = function (context, map, depArray) {
//                        alert('test');
//                    };
//                    iframe.height(height + 'px');
//                    iframe.width(width + 'px');
                    onePageTile = $('#' + tileId);
                    $('#' + tileId).height(height + 'px');
                    $('#' + tileId).width(width + 'px');
                    if (!$('#df_iframe').hasClass('dbd-one-page'))
                        $('#df_iframe').addClass('dbd-one-page');
                    $('#df_iframe').width((width - 5) + 'px');
                }
            };
            
            self.postTileMenuClicked = function(tile) {
                $("#tileMenu" + tile.clientGuid).hide();
                if ($('#actionButton' + tile.clientGuid).hasClass('oj-selected')) {
                    $('#actionButton' + tile.clientGuid).removeClass('oj-selected');
                    $('#actionButton' + tile.clientGuid).addClass('oj-default');
                }
            };
            
            self.maximize = function(tile) {
                for (var i = 0; i < self.tiles().length; i++) {
                    var eachTile = self.tiles()[i];
                    if (eachTile !== tile)
                        eachTile.shouldHide(true);
                }
                tile.shouldHide(false);
                tile.maximized(true);
                self.tilesView.disableSortable();
                self.tilesView.disableDraggable();
                var maximizedTileHeight = self.calculateTilesRowHeight();
                self.tileOriginalHeight = $('.dbd-tile-maximized .dbd-tile-element').height();
                $('.dbd-tile-maximized .dbd-tile-element').height(maximizedTileHeight);
                $('#add-widget-button').ojButton('option', 'disabled', true);
                
                self.postTileMenuClicked(tile);
            };
            
            self.restore = function(tile) {
                if (self.tileOriginalHeight) {
                    $('.dbd-tile-maximized .dbd-tile-element').height(self.tileOriginalHeight);
                }
                $('#add-widget-button').ojButton('option', 'disabled', false);
                tile.maximized(false);
                for (var i = 0; i < self.tiles().length; i++) {
                    var eachTile = self.tiles()[i];
                    eachTile.shouldHide(false);
                }
                self.tilesView.enableSortable();
                self.tilesView.enableDraggable();
                
                self.postTileMenuClicked(tile);
            };
            
            self.changeUrl = function(tile) {
                urlEditView.setEditedTile(tile);
                $('#urlChangeDialog').ojDialog('open');
                
                self.postTileMenuClicked(tile);
            };
            
            self.fireDashboardItemChangeEventTo = function (widget, dashboardItemChangeEvent) {
                var deferred = $.Deferred();
                $.ajax({url: 'widgetLoading.html',
                    widget: widget,
                    success: function () {
                        /**
                         * A widget needs to define its parent's onDashboardItemChangeEvent() method to resposne to dashboardItemChangeEvent
                         */
                        if (this.widget.onDashboardItemChangeEvent) {
                            this.widget.onDashboardItemChangeEvent(dashboardItemChangeEvent);
                            console.log(widget.title());
                            deferred.resolve();
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log(textStatus);
                        deferred.reject(textStatus);
                    }
                });
                return deferred.promise();
            };

            self.fireDashboardItemChangeEvent = function(dashboardItemChangeEvent){
                if (dashboardItemChangeEvent){
                    var defArray = [];
                    for (i = 0; i < self.tiles().length; i++) {
                        var aTile = self.tiles()[i];
                        defArray.push(self.fireDashboardItemChangeEventTo(aTile,dashboardItemChangeEvent));
                    }

                    var combinedPromise = $.when.apply($,defArray);
                    combinedPromise.done(function(){
                        console.log("All Widgets have completed refresh!");
                    });
                    combinedPromise.fail(function(ex){
                        console.log("One or more widgets failed to refresh: "+ex);
                    });   
                }
            };
            
            self.postDocumentShow = function() {
                self.maximizeFirst();
            };

            var sliderChangelistener = ko.computed(function(){
                    return {
                        timeRangeChange:timeSliderModel.timeRangeChange(),
                        advancedOptionsChange:timeSliderModel.advancedOptionsChange(),
                        timeRangeViewChange:timeSliderModel.timeRangeViewChange()
                    };
                });
                
            sliderChangelistener.subscribe(function (value) {
                if (value.timeRangeChange){
                    var dashboardItemChangeEvent = new DashboardItemChangeEvent(new DashboardTimeRangeChange(timeSliderModel.viewStart,timeSliderModel.viewEnd),null);
                    self.fireDashboardItemChangeEvent(dashboardItemChangeEvent);
                    timeSliderModel.timeRangeChange(false);
                }else if (value.timeRangeViewChange){
                    timeSliderModel.timeRangeViewChange(false);
                }else if (value.advancedOptionsChange){
                    timeSliderModel.advancedOptionsChange(false);
                }
            });

            var timeSelectorChangelistener = ko.computed(function(){
                    return {
                        timeRangeChange:self.timeSelectorModel.timeRangeChange()
                    };
                });
                
            timeSelectorChangelistener.subscribe(function (value) {
                if (value.timeRangeChange){
                    var dashboardItemChangeEvent = new DashboardItemChangeEvent(new DashboardTimeRangeChange(self.timeSelectorModel.viewStart,self.timeSelectorModel.viewEnd),null);
                    self.fireDashboardItemChangeEvent(dashboardItemChangeEvent);
                    self.timeSelectorModel.timeRangeChange(false);
                }
            });            
            /* event handler for button to get screen shot */
//            self.screenShotClicked = function(data, event) {
//                var images = self.images;
//                var renderWhole = self.renderWholeScreenShot;
//                var tileFrames = $('.dbd-tile-element div iframe');
//                var sizeTiles = tileFrames.size();
//                var handled = 0;
//                tileFrames.each(function(idx, elem){
//                    /*try {
//                        var dom = elem.contentWindow.document;
//                        var domHead = dom.getElementsByTagName('head').item(0);
//                        $("<script src='http://localhost:8383/emcpssf/js/libs/html2canvas/html2canvas.js' type='text/javascript'></script>").appendTo(domHead);
//                    } catch (ex) {
//                        // Security Error
//                    }*/
//                    elem.contentWindow.postMessage({index: idx, type: "screenShot"},"*");
//                    /*html2canvas(elem.contentWindow.$('body'), {
//                        onrendered: function(canvas) {  
//                            var tileData = canvas.toDataURL();
//                            images.splice(images().length, 0, new DashboardTileImage(tileData));
//                            handled++;
//                            if (handled === sizeTiles) {
//                                renderWhole();
//                            }
//                        }  
//                    });*/
//                });
//            };
        }
        
        function DashboardViewModel() {
            var self = this;
            
            self.name = observable("LaaS Dashboard");
            self.description = observable("Use dashbaord builder to edit, maintain, and view tiles for search results.");
        }
        
        return {"DashboardWidget": DashboardWidget, 
            "DashboardTilesViewModel": DashboardTilesViewModel,
            "DashboardViewModel": DashboardViewModel};
    }
);

// global variable for iframe dom object
var globalDom;
// original height for document inside iframe
var originalHeight;
// tile used to wrapper the only widget inside one page dashboard
var onePageTile;

// check the height of document inside iframe repeatedly
function waitForIFrameLoading() {
//  if ($('#df_iframe').context.readyState != "complete") {
    var height = globalDom.scrollHeight;
    if (originalHeight === height) {
        console.log('height for document is ' + height);
        setTimeout("waitForIFrameLoading();", 200);
    } else {
        onePageIframeLoaded();
    }
}

function onePageIframeLoaded() {
    alert($('#df_iframe').contents().find("body").height());
    alert('iframe state changed to completed');
    console.log('height for document is ' + height);
}
