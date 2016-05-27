/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
define(['knockout', 
        'jquery',
        'dfutil',
        'mobileutil',
        'uiutil',
        'ojs/ojcore',
        'builder/tool-bar/edit.dialog',
        'jqueryui',
        'builder/builder.core',
        'builder/widget/widget.model'
    ], 
    function(ko, $, dfu, mbu, uiutil, oj, ed) {
        function ResizableView($b) {
            var self = this;
            
            self.initialize = function() {
                $b.addBuilderResizeListener(self.onResizeFitSize);
            };
            
            self.onResizeFitSize = function(width, height, leftWidth, topHeight) {
                self.rebuildElementSet(),
                self.$list.each(function() {
                    var elem = $(this)
                    , v_siblings = elem.siblings(".fit-size-vertical-sibling:visible")
                    , h = 54;
                    if(v_siblings && 1 == v_siblings.length && v_siblings.attr("class").indexOf("dbd-right-panel-title")>-1){
                        h = 0;
                    }
                    if (v_siblings && v_siblings.length > 0) {
                        for (var i = 0; i < v_siblings.length; i++) {
                            h += $(v_siblings[i]).outerHeight();
                        }
                        elem.height(height - topHeight - h);
                    }
                });
            };
            
            self.rebuildElementSet = function() {
                self.$list = $b.findEl(".fit-size");
            };
            
            self.initialize();
        }
        
        function RightPanelModel($b, tilesViewModel, toolBarModel, dashboardsetToolBarModel) {
            var self = this;
            self.dashboardsetToolBarModel = dashboardsetToolBarModel;
            self.dashboard = $b.dashboard;
            self.tilesViewModel = tilesViewModel;
            $b.registerObject(this, 'RightPanelModel');

            self.isMobileDevice = ((new mbu()).isMobile === true ? 'true' : 'false');
            self.scrollbarWidth = uiutil.getScrollbarWidth();
            
            self.emptyDashboard = tilesViewModel && tilesViewModel.isEmpty();
            
            self.keyword = ko.observable('');
            self.clearRightPanelSearch=ko.observable(false);
            self.widgets = ko.observableArray([]);

            self.completelyHidden = ko.observable(self.isMobileDevice === 'true' || !self.emptyDashboard);
            self.maximized = ko.observable(false);
            
            self.editDashboardDialogModel = new ed.EditDashboardDialogModel($b, toolBarModel);
            
            self.dbfiltersIsExpanded = ko.observable(false);
            self.sharesettingsIsExpanded = ko.observable(false);
            self.dbeditorIsExpanded = ko.observable(true);
            
            var scrollInstantStore = ko.observable();
            var scrollDelay = ko.computed(function() { 
                return scrollInstantStore();
            });
            scrollDelay.extend({ rateLimit: { method: "notifyWhenChangesStop", timeout: 400 } }); 
            
            self.widgetListScroll = function(data, event) {
                scrollInstantStore(event.target.scrollTop);
            };
            
            var widgetListHeight = ko.observable($(".dbd-left-panel-widgets").height());
            var $dbdLeftPanelWidgets = $b.findEl(".dbd-left-panel-widgets");
            if(typeof window.MutationObserver !== 'undefined'){
                var widgetListHeightChangeObserver = new MutationObserver(function(){
                    widgetListHeight($dbdLeftPanelWidgets.height());
                });
                widgetListHeightChangeObserver.observe($dbdLeftPanelWidgets[0],{
                    attributes: true,
                    attrbuteFilter: ['style']
                });
            }else{
                $b.addBuilderResizeListener(function(){
                    widgetListHeight($dbdLeftPanelWidgets.height());
                });
            }
            widgetListHeight.subscribe(function(){
                loadSeeableWidgetScreenshots();
            });
            
            var loadSeeableWidgetScreenshots = function(startPosition){
                var fromWidgetIndex = startPosition?(Math.floor(startPosition/30)):0;
                var toWidgetIndex = Math.ceil(widgetListHeight()/30)+fromWidgetIndex;
                if (self.widgets && self.widgets().length > 0) {
                    for (var i = fromWidgetIndex; i < toWidgetIndex; i++) {
                        if (self.widgets()[i]&&!self.widgets()[i].WIDGET_VISUAL()){
                            self.getWidgetScreenshot(self.widgets()[i]);
                        }
                    }
                }
            };
            scrollDelay.subscribe(function(val){
                loadSeeableWidgetScreenshots(val);
            });
            

//            self.showTimeControl = ko.observable(false);
            // observable variable possibly updated by other events
//            self.enableTimeControl = ko.observable(false);
//            self.computedEnableTimeControl = ko.pureComputed({
//                read: function() {
//                    console.debug('LeftPanel enableTimeControl is ' + self.enableTimeControl() + ', ' + (self.enableTimeControl()?'Enable':'Disable')+' time control settings accordingly');
//                    return self.enableTimeControl();
//                },
//                write: function(value) {
//                    console.debug('Time control settings is set to ' + value + ' manually');
//                    self.enableTimeControl(value);
//                    self.dashboard.enableTimeRange(value?'TRUE':'FALSE');
//                    $b.triggerEvent($b.EVENT_DSB_ENABLE_TIMERANGE_CHANGED, null);
//                }
//            });

//            self.dashboardTileExistsChangedHandler = function(anyTileExists) {
//                console.debug('Received event EVENT_TILE_EXISTS_CHANGED with value of ' + anyTileExists + '. ' + (anyTileExists?'Show':'Hide') + ' time control settings accordingly');
//    //                self.showTimeControl(anyTileExists);
//            };

//            self.dashboardTileSupportTimeControlHandler = function(exists) {
//                console.debug('Received event EVENT_EXISTS_TILE_SUPPORT_TIMECONTROL with value of ' + exists + '. ' + (exists?'Show':'Hide') + ' time control settings accordingly');
//                if (self.dashboard.enableTimeRange() === 'AUTO') {
//                    console.debug('As dashboard enable time range is AUTO, '+(exists?'enable':'disable') + ' time control settings based result if tile supporting time control exists. Its value is ' + exists);
//                    self.enableTimeControl(exists);
//                }
//                else {
//                    console.debug((self.dashboard.enableTimeRange()==='TRUE'?'Enable':'Disable') + ' time control based on dashboard enableTimeRange value: ' + self.dashboard.enableTimeRange());
//                    self.enableTimeControl(self.dashboard.enableTimeRange() === 'TRUE');
//                }
//                console.debug('Exists tile supporting time control? ' + exists + ' ' + (exists?'Show':'Hide') + ' time control setting accordingly');
//                self.showTimeControl(exists);
//            };

            self.initialize = function() {
                    if (self.dashboard.type() === 'SINGLEPAGE' || self.dashboard.systemDashboard()) {
                        self.completelyHidden(true);
                        $b.triggerBuilderResizeEvent('OOB dashboard detected and hide left panel');
                    }
//                self.completelyHidden(true);
//                $b.triggerBuilderResizeEvent('Hide left panel in sprint47');

                    self.initEventHandlers();
                    self.loadWidgets();
                    self.initDraggable();
//                    self.checkAndDisableLinkDraggable();

                    $b.findEl('.widget-search-input').autocomplete({
                        source: self.autoSearchWidgets,
                        delay: 700,
                        minLength: 0
                    });
            };

            self.initEventHandlers = function() {
//                $b.addBuilderResizeListener(self.resizeEventHandler);
                $b.addEventListener($b.EVENT_TILE_MAXIMIZED, self.tileMaximizedHandler);
                $b.addEventListener($b.EVENT_TILE_RESTORED, self.tileRestoredHandler);
//                $b.addEventListener($b.EVENT_TILE_ADDED, self.tileAddedHandler);
//                $b.addEventListener($b.EVENT_TILE_DELETED, self.tileDeletedHandler);
//                $b.addEventListener($b.EVENT_TILE_EXISTS_CHANGED, self.dashboardTileExistsChangedHandler);
//                $b.addEventListener($b.EVENT_EXISTS_TILE_SUPPORT_TIMECONTROL, self.dashboardTileSupportTimeControlHandler);
            };

            self.initDraggable = function() {
                self.initWidgetDraggable();
//                self.initTextWidgetDraggable();
//                self.initWidgetLinkDraggable();
            };

            self.initWidgetDraggable = function() {
                $b.findEl(".dbd-left-panel-widget-text").draggable({
                    helper: "clone",
                    scroll: false,
                    start: function(e, t) {
                        $b.triggerEvent($b.EVENT_NEW_WIDGET_START_DRAGGING, null, e, t);
                    },
                    drag: function(e, t) {
                        $b.triggerEvent($b.EVENT_NEW_WIDGET_DRAGGING, null, e, t);
                    },
                    stop: function(e, t) {
                        $b.triggerEvent($b.EVENT_NEW_WIDGET_STOP_DRAGGING, null, e, t);
                    }
                });
            };

//            self.initTextWidgetDraggable = function() {
//                $("#dbd-left-panel-text").draggable({
//                    helper: "clone",
//                    handle: "#dbd-left-panel-text-handle",
//                    start: function(e, t) {
//                        $b.triggerEvent($b.EVENT_NEW_TEXT_START_DRAGGING, null, e, t);
//                    },
//                    drag: function(e, t) {
//                        $b.triggerEvent($b.EVENT_NEW_TEXT_DRAGGING, null, e, t);
//                    },
//                    stop: function(e, t) {
//                        $b.triggerEvent($b.EVENT_NEW_TEXT_STOP_DRAGGING, null, e, t);
//                    }
//                });
//            };

//            self.initWidgetLinkDraggable = function() {
//                $("#dbd-left-panel-link").draggable({
//                    helper: "clone",
//                    handle: "#dbd-left-panel-link-handle",
//                    start: function(e, t) {
//                        $b.triggerEvent($b.EVENT_NEW_LINK_START_DRAGGING, null, e, t);
//                    },
//                    drag: function(e, t) {
//                        $b.triggerEvent($b.EVENT_NEW_LINK_DRAGGING, null, e, t);
//                    },
//                    stop: function(e, t) {
//                        $b.triggerEvent($b.EVENT_NEW_LINK_STOP_DRAGGING, null, e, t);
//                    }
//                });       
//            };

//            self.resizeEventHandler = function(width, height, leftWidth, topHeight) {
//                $('#dbd-left-panel').height(height - topHeight);
//                $('#left-panel-text-helper').css("width", width - 20);
//            };

            self.tileMaximizedHandler = function() {
                self.maximized(true);
                $b.triggerBuilderResizeEvent('tile maximized and completely hide left panel');
            };

            self.tileRestoredHandler = function() {
                self.maximized(false);
                self.initDraggable();
                $b.triggerBuilderResizeEvent('hide left panel because restore');
            };
            
//            self.tileAddedHandler = function(tile) {
//                tile && tile.type() === "DEFAULT" && ($("#dbd-left-panel-link").draggable("enable"));
//            };

//            self.tileDeletedHandler = function(tile) {
//                if (!tile || tile.type() !== "DEFAULT")
//                    return;
//                self.checkAndDisableLinkDraggable();
//            };
            var AUTO_PAGE_NAV = 1;
            var widgetListHeight = ko.observable(0);
            var pageSizeLastTime = 0;
            // try using MutationObserver to detect widget list height change.
            // if MutationObserver is not availbe, register builder resize listener.
            if (typeof window.MutationObserver !== 'undefined') {
                var widgetListHeightChangeObserver = new MutationObserver(function () {
                    widgetListHeight($(".dbd-left-panel-widgets").height());
                });
                widgetListHeightChangeObserver.observe($(".dbd-left-panel-widgets")[0], {
                    attributes: true,
                    attributeFilter: ['style']
                });
            } else {
                $b.addBuilderResizeListener(function () {
                    widgetListHeight($(".dbd-left-panel-widgets").height());
                });
            }
            // for delay notification.
            widgetListHeight.extend({rateLimit: 500, method: 'notifyWhenChangesStop '});
            widgetListHeight.subscribe(function () {
                console.log("loaded");
                self.loadWidgets(null, AUTO_PAGE_NAV);
            });


            self.loadWidgets = function(req) {                
                var widgetDS = new Builder.WidgetDataSource();
                
                widgetDS.loadWidgetData(
                    req && (typeof req.term === "string") ? req.term : self.keyword(),
                    function (widgets) {
                        self.widgets([]);
                        if (widgets && widgets.length > 0) {
                            for (var i = 0; i < widgets.length; i++) {
                                if (!widgets[i].WIDGET_DESCRIPTION)
                                    widgets[i].WIDGET_DESCRIPTION = null;
                                var wgt = ko.mapping.fromJS(widgets[i]);
                                wgt && !wgt.WIDGET_VISUAL && (wgt.WIDGET_VISUAL = ko.observable(''));
//                                self.getWidgetScreenshot(wgt);
                                self.widgets.push(wgt);
                            }
                        }
                        self.initWidgetDraggable();                       
                    }
                );
            };

            self.getWidgetScreenshot = function(wgt) {
                var url = null;
                wgt.WIDGET_SCREENSHOT_HREF && (url = wgt.WIDGET_SCREENSHOT_HREF());
//                if (!url) { // backward compility if SSF doesn't support .png screenshot. to be removed once SSF changes are merged
//                    loadWidgetBase64Screenshot(wgt);
//                    return;
//                }
                if (!dfu.isDevMode()){
                    url = dfu.getRelUrlFromFullUrl(url);
                } 
                wgt && !wgt.WIDGET_VISUAL && (wgt.WIDGET_VISUAL = ko.observable(''));
                url && wgt.WIDGET_VISUAL(url);
                !wgt.WIDGET_VISUAL() && (wgt.WIDGET_VISUAL('@version@/images/sample-widget-histogram.png'));
            };
            
            self.getWidgetBase64Screenshot = function(wgt) {
                var url = '/sso.static/savedsearch.widgets';
                dfu.isDevMode() && (url = dfu.buildFullUrl(dfu.getDevData().ssfRestApiEndPoint,'/widgets'));
                url += '/'+wgt.WIDGET_UNIQUE_ID()+'/screenshot';
                wgt && !wgt.WIDGET_VISUAL && (wgt.WIDGET_VISUAL = ko.observable(''));
                dfu.ajaxWithRetry({
                    url: url,
                    headers: dfu.getSavedSearchRequestHeader(),
                    success: function(data) {
                        data && (wgt.WIDGET_VISUAL(data.screenShot));
                        !wgt.WIDGET_VISUAL() && (wgt.WIDGET_VISUAL('@version@/images/sample-widget-histogram.png'));
                    },
                    error: function() {
                        oj.Logger.error('Error to get widget screen shot for widget with unique id: ' + wgt.WIDGET_UNIQUE_ID);
                        !wgt.WIDGET_VISUAL() && (wgt.WIDGET_VISUAL('@version@/images/sample-widget-histogram.png'));
                    },
                    async: true
                });
            };

            self.searchWidgetsInputKeypressed = function(e, d) {
                if (d.keyCode === 13) {
                    self.searchWidgetsClicked();
                    return false;
                }
                return true;
            };

            self.searchWidgetsClicked = function() {
                self.loadWidgets();
            };
            
            self.autoSearchWidgets = function(req) {
                self.loadWidgets(req);
                if (req.term.length === 0) {
                    self.clearRightPanelSearch(false);
                }else{
                    self.clearRightPanelSearch(true);
                }
            };

            self.clearWidgetSearchInputClicked = function() {
                if (self.keyword()) {
                    self.keyword(null);
                    self.searchWidgetsClicked();
                    self.clearRightPanelSearch(false);
                }
            };
            
            self.toggleLeftPanel = function() {
                if (!self.completelyHidden()) {
                    self.completelyHidden(true);
                    $b.triggerBuilderResizeEvent('hide left panel');
                } 
                else {
                    self.dbeditorIsExpanded(true);
                    self.completelyHidden(false);
                    self.initDraggable();
                    $b.triggerBuilderResizeEvent('show left panel');
                }
            };

            self.widgetMouseOverHandler = function(widget,event) {
                if($('.ui-draggable-dragging') && $('.ui-draggable-dragging').length > 0)
                    return;
                if(!widget.WIDGET_VISUAL())
                    self.getWidgetScreenshot(widget);
                var widgetItem=$(event.currentTarget).closest('.widget-item-'+widget.WIDGET_UNIQUE_ID());
                var popupContent=$(widgetItem).find('.dbd-left-panel-img-pop');
                if (!popupContent.ojPopup("isOpen")) {
                   $(popupContent).ojPopup("open", $(widgetItem), 
                   {
                       my : "end center", at : "start center"
                   });
               }
            };

            self.widgetMouseOutHandler = function(widget) {              
                if ($('.widget-'+widget.WIDGET_UNIQUE_ID()).ojPopup("isOpen")) {
                    $('.widget-'+widget.WIDGET_UNIQUE_ID()).ojPopup("close");
                }
            };
            
            self.widgetKeyPress = function(widget, event) {
                if (event.keyCode === 13) {
                   tilesViewModel.appendNewTile(widget.WIDGET_NAME(), "", 4, 2, ko.toJS(widget));
                }
            };
            
            self.containerMouseOverHandler = function() {
                if($('.ui-draggable-dragging') && $('.ui-draggable-dragging').length > 0)
                    return;
                if (!$b.findEl('.right-container-pop').ojPopup("isOpen")) {
                   $b.findEl('.right-container-pop').ojPopup("open", $b.findEl('.dbd-left-panel-footer-contain'), 
                   {
                       my : "end bottom", at : "start-25 bottom"
                   });
                }
            };

            self.containerMouseOutHandler = function() {
                if ($b.findEl('.right-container-pop').ojPopup("isOpen")) {
                    $b.findEl('.right-container-pop').ojPopup("close");
                }
            };

//            self.checkAndDisableLinkDraggable = function() {
//                if(!self.dashboard.isDefaultTileExist()) {
//                    $("#dbd-left-panel-link").draggable("disable");
//                }
//            };

            self.deleteDashboardClicked = function(){
                toolBarModel.openDashboardDeleteConfirmDialog();
            };        

            self.dbfiltersIsExpanded.subscribe(function(val){
                if(val){
                    if(!self.dashboardsetToolBarModel.isDashboardSet()){
                        self.dbfiltersIsExpanded(true);
                    }
                    self.sharesettingsIsExpanded(false);
                    self.dbeditorIsExpanded(false);
                }
            });
            self.sharesettingsIsExpanded.subscribe(function(val){
                if(val){
                    self.dbfiltersIsExpanded(false);
                    self.dbeditorIsExpanded(false);
                }
            });
            self.dbeditorIsExpanded.subscribe(function(val){
                if(val){
                    self.dbfiltersIsExpanded(false);
                    self.sharesettingsIsExpanded(false);
                }else{
                    toolBarModel.onNameOrDescriptionEditing = false;
                }
            });
            

            self.showdbOnHomePage = ko.observable([]);
            
            var dsbSaveDelay = ko.computed(function(){
                return self.editDashboardDialogModel.showdbDescription()+self.editDashboardDialogModel.name()+self.editDashboardDialogModel.description()+self.showdbOnHomePage();
            });
            dsbSaveDelay.extend({ rateLimit: { method: "notifyWhenChangesStop", timeout: 800 } }); 
            dsbSaveDelay.subscribe(function(){
                self.editDashboardDialogModel.save();
            });
            
            self.enableEntityFilter = ko.observable(true);
            self.instanceSupport = ko.observable("multiple");
            self.enableTimeRangeFilter = ko.observable(true);
            self.defaultEntityValue = ko.observable("allEntities");
            self.filterSettingModified = ko.observable(false);
            var filterSettingModified = ko.computed(function(){
                return self.instanceSupport()+self.enableEntityFilter()+self.enableTimeRangeFilter()+self.defaultEntityValue();
            });
            filterSettingModified.subscribe(function(val){
                self.filterSettingModified(true);
            });
            self.applyFilterSetting = function(){
                //add save filter setting logic here
                self.filterSettingModified(false);
            };
            
            self.dashboardSharing = ko.observable("notShared");
            self.dashboardSharing.subscribe(function(val){
                if("notShared"===val){
                    self.enableShareAutoRefresh(false);
                    toolBarModel.handleShareUnshare(false);
                }else{
                    self.enableShareAutoRefresh(true);
                    toolBarModel.handleShareUnshare(true);
                }
            });
            self.defaultAutoRefreshValue = ko.observable("every5minutes");
            self.enableShareAutoRefresh = ko.observable(false);

            self.enableEntityFilter.subscribe(function(val){
                if(val){
                    $(".enableEntityFilter").css("color","#333");
                }else{
                    $(".enableEntityFilter").css("color","#9e9e9e");
                }
            });

            self.enableTimeRangeFilter.subscribe(function(val){
                if(val){
                    $(".enableTimeRangeFilter").css("color","#333");
                }else{
                    $(".enableTimeRangeFilter").css("color","#9e9e9e");
                }
            });

            self.enableShareAutoRefresh.subscribe(function(val){
                if(val){
                    $(".enableShareAutoRefresh").css("color","#333");
                }else{
                    $(".enableShareAutoRefresh").css("color","#9e9e9e");
                }
            });
            if(self.dashboardsetToolBarModel.isDashboardSet()){
            self.dashboardsetName = ko.observable(self.dashboardsetToolBarModel.dashboardsetName());
            self.dashboardsetDescription = ko.observable(self.dashboardsetToolBarModel.dashboardsetDescription());
            self.dashboardsetNameInputed = ko.observable(self.dashboardsetName());
            self.dashboardsetDescriptionInputed = ko.observable(self.dashboardsetDescription());
            self.dashboardsetShare = ko.observable("off");
            self.dashboardsetName.subscribe(function(val){
                $('#nameDescription input').val(val);
            });
            self.dashboardsetDescription.subscribe(function(val){
                $('#nameDescription textarea').val(val);
            });
            self.dashboardsetNameInputed.subscribe(function(val){
                self.dashboardsetName(val);
            });
            self.dashboardsetDescriptionInputed.subscribe(function(val){
                self.dashboardsetDescription(val);
            });
            var dsbSetSaveDelay = ko.computed(function() { 
                return self.dashboardsetName()+self.dashboardsetDescription()+self.dashboardsetShare();
            });
            dsbSetSaveDelay.extend({ rateLimit: { method: "notifyWhenChangesStop", timeout: 800 } }); 
            dsbSetSaveDelay.subscribe(function(){
                self.dashboardsetToolBarModel.dbConfigMenuClick.saveDbsDescription({dashboardsetConfig:{share:self.dashboardsetShare}});
            });
            self.deleteDashboardSetClicked = function(){
                $('#deleteDashboardset').ojDialog("open");
            };  
        }

            self.editPanelContent = ko.observable(function(){
                var content = "settings";
                return content;
            }());
            
            self.switchEditPanelContent = function(data,event){    
                if($(event.currentTarget).hasClass('edit-dsb-link')){
                    self.editPanelContent("edit");
                        self.dbeditorIsExpanded(true);
                }else if($(event.currentTarget).hasClass('edit-dsbset-link')){
                    self.editPanelContent("editset");
                    self.dbeditorIsExpanded(true);
                }else{
                    self.sharesettingsIsExpanded(false);
                    self.dbfiltersIsExpanded(false);
                    self.dbeditorIsExpanded(false);
                    self.editPanelContent("settings");
                    $(".dbd-right-panel-editdashboard-share>div").css("display","none");
                    $(".dbd-right-panel-editdashboard-filters>div").css("display","none");
                }
                $b.triggerBuilderResizeEvent('OOB dashboard detected and hide left panel');
            };
        }
        
        Builder.registerModule(RightPanelModel, 'RightPanelModel');
        Builder.registerModule(ResizableView, 'ResizableView');

        return {"RightPanelModel": RightPanelModel, "ResizableView": ResizableView};
    }
);

