/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
define([
    'knockout', 
    'jquery',
    'dfutil'
],
    function(ko, $, dfu)
    {
        function DemoLogAnalyticsViewModel(params) {
            var self = this;
            var startTime = null; 
            var endTime = null;
//            var qlBaseUrl = 'http://slc00aeg.us.oracle.com:7601/emaas/querylanguage/api/v2/';
//            var qlBaseUrl = 'http://slc06fev.us.oracle.com:7001/emaas/querylanguage/api/v2/'; 
            var qlBaseUrl = 'http://slc08upj.us.oracle.com:7601/emaas/querylanguage/api/v2/';
            var queryString = {"queries": {"id":"demoEMQLVizQuery",  "queryString" : "* | stats count by 'target type','log source'"}, 
                "subSystem":"LOG",
                "timeFilter" : null};
            var barSeries = [];
            var barGroups = [];
            var widget = params.tile.widget;
            if (widget && widget.id) {
                var ssfUrl = dfu.discoverSavedSearchServiceUrl();
                if (ssfUrl && ssfUrl !== '') {
                    var searchUrl = ssfUrl + '/search/'+widget.id;
                    $.ajax({
                        url: searchUrl,
                        success: function(data, textStatus) {
                            queryString.queries.queryString = data.queryStr;
                        },
                        error: function(xhr, textStatus, errorThrown){
                            console.log('Error when querying log analytics saved searches!');
                        },
                        async: false
                    });
                }
            }
            self.seriesValue = ko.observableArray(barSeries);
            self.groupsValue = ko.observableArray(barGroups);
            self.chartType = ko.observable('bar');
            
            fetchResults();
            
            params.tile.onDashboardItemChangeEvent = function(dashboardItemChangeEvent){
                if (dashboardItemChangeEvent){
                    if (dashboardItemChangeEvent.timeRangeChange){
                        startTime = dashboardItemChangeEvent.timeRangeChange.viewStartTime();
                        endTime = dashboardItemChangeEvent.timeRangeChange.viewEndTime();
                        refresh(startTime, endTime);
                    }
                }
            };
            
            function refresh(startTime, endTime) {
                if (startTime === null && endTime === null) {
                    queryString.timeFilter = null;
                }
                else {
                    var startTimeStr = (startTime === null ? null : startTime.toISOString());
                    var endTimeStr = (endTime === null ? null : endTime.toISOString());
                    if (queryString.timeFilter === null) {
                        queryString.timeFilter = {};
                    }
                    queryString.timeFilter.type = "absolute";
                    queryString.timeFilter.startTime = startTimeStr;
                    queryString.timeFilter.endTime = endTimeStr;
                    fetchResults();
                }
            };
            
            function fetchResults() {
                $.ajax({type: 'POST', contentType:'application/json',url: qlBaseUrl+'jobs', data: ko.toJSON(queryString),
                    headers:{'X-USER-IDENTITY-DOMAIN-NAME':"TenantOPC1"},
                    success: function(data, textStatus){
                        var resultsUrl = qlBaseUrl + data.job.queries.demoEMQLVizQuery.resultsLink; 
                        $.ajax({type: 'GET', contentType:'application/json',url: resultsUrl,
                            headers:{'X-USER-IDENTITY-DOMAIN-NAME':"TenantOPC1"},
                            success: function(data, textStatus){
                                if (data.results && data.results.length > 0) {
                                    fetchResultSuccessCallBack(data);
                                }
                                else {
                                    barSeries = [];
                                    barGroups = [];
                                }
                                self.seriesValue(barSeries);
                                self.groupsValue(barGroups);
                                
                            },
                            error: function(data, textStatus){
                                console.log('Error when fetching results data from ' + resultsUrl + '!');
                            }
                        });
                    },
                    error: function(data, textStatus){
                        console.log('Error when submitting a query job!');
                    }
                });
            };
            
            function fetchResultSuccessCallBack(data) {
//                var targetTypeValues = convertToNameValueObject(data.columns[0].values);
//                var logSrcValues = convertToNameValueObject(data.columns[1].values);
//                var resultsData = data.results;
//                var barChartGrps = getGroups(targetTypeValues, resultsData);
//                var series = getSeries(logSrcValues, resultsData); 
                
                var targetTypeValues = {};
                var logSrcValues = {};
                var resultsData = data.results;
                var barChartGrps = [];
                var series = []; 
                if (data.columns.length === 3) {
                    self.chartType('polar');
                    if (data.columns[0].values) {
                        targetTypeValues = convertToNameValueObject(data.columns[0].values);
                        barChartGrps = getGroups(targetTypeValues, resultsData);
                    }
                    else {
                        barChartGrps = getGroups(null, resultsData);
                        targetTypeValues = convertToNameValueObject(barChartGrps);
                    }
                    if (data.columns[1].values) {
                        logSrcValues = convertToNameValueObject(data.columns[1].values);
                        series = getSeries(logSrcValues, resultsData);
                    }
                    else {
                        series = getSeries(null, resultsData);
                        logSrcValues = convertToNameValueObject(series);
                    }
                }
                else if (data.columns.length === 2) {
                    self.chartType('bar');
                    if (data.columns[0].values) {
                        targetTypeValues = convertToNameValueObject(data.columns[0].values);
                        barChartGrps = getGroups(targetTypeValues, resultsData);
                    }
                    else {
                        barChartGrps = getGroups(null, resultsData);
                        targetTypeValues = convertToNameValueObject(barChartGrps);
                    }   
                    series = [data.columns[1].displayName];
                    logSrcValues = convertToNameValueObject(series);
                }
                else {
                    self.chartType('bar');
                }

                var barChartSeries = [];
                for (var i = 0; i < series.length; i++) {
                    var seriesItem = {name: series[i]};
                    var items = initializeSeriesItems(barChartGrps.length);
                    if (data.columns.length === 3) {
                        for (var j = 0; j < resultsData.length; j++) { 
                            if (logSrcValues[resultsData[j][1]] === series[i]) {
                                items[getItemIndex(barChartGrps, targetTypeValues[resultsData[j][0]])] = resultsData[j][2];
                            }
                        }
                    }
                    else if (data.columns.length === 2){
                        for (var k = 0; k < barChartGrps.length; k++) { 
                            for (var m = 0; m < resultsData.length; m++) {
                                if (targetTypeValues[resultsData[m][0]] === barChartGrps[k]) {
                                    items[k] = items[k] + resultsData[m][1];
                                }
                            }
                        }
                    }
                    
                    seriesItem.items = items;
                    barChartSeries.push(seriesItem);
                }
                
                barSeries = barChartSeries; 
                barGroups = barChartGrps;
            };
            
            function getGroups(targetTypeValues, resultsData) {
                var groups = [];
                if (targetTypeValues !== null) {
                    for (var i = 0; i < resultsData.length; i++) {
                        if (!isContains(groups, targetTypeValues[resultsData[i][0]])) {
                            groups.push(targetTypeValues[resultsData[i][0]]);
                        } 
                    }
                }
                else {
//                    targetTypeValues = {};
                    for (var i = 0; i < resultsData.length; i++) {
                        if (!isContains(groups, resultsData[i][0])) {
                            groups.push(resultsData[i][0]);
//                            targetTypeValues[resultsData[i][0]] = resultsData[i][0];
                        } 
                    }
                }
                
                return groups;
            };
            
            function getSeries(logSrcValues, resultsData) {
                var series = [];
                if (logSrcValues !== null) {
                    for (var i = 0; i < resultsData.length; i++) {
                        if (!isContains(series, logSrcValues[resultsData[i][1]])) {
                            series.push(logSrcValues[resultsData[i][1]]);
                        } 
                    }
                }
                else {
//                    logSrcValues = {};
                    for (var i = 0; i < resultsData.length; i++) {
                        if (!isContains(series, resultsData[i][1])) {
                            series.push(resultsData[i][1]+"");
//                            logSrcValues[resultsData[i][1]] = resultsData[i][1];
                        } 
                    }
                }
                
                return series;
            };
            
            function isContains(array, item) { 
                var i = array.length; 
                while (i--) { 
                    if (array[i] === item) { 
                        return true; 
                    } 
                } 
                return false; 
            };
            
            function convertToNameValueObject(array) {
                var resultObj = {};
                for (var i = 0; i < array.length; i++) {
                    if (array[i] !== null && array[i].internalValue && array[i].displayValue) {
                        resultObj[array[i].internalValue] = array[i].displayValue;
                    }
                    else {
                        resultObj[array[i]] = array[i];
                    }
                }
                
                return resultObj;
            };
            
            function initializeSeriesItems(size) {
                var items = [];
                for (var i = 0; i < size; i++) {
                    items[i] = 0;
                }
                return items;
            }
            
            function getItemIndex(itemList, item) { 
                //item = (item === null? 'N/A' : item);
                for (var i = 0; i < itemList.length; i++) {
                    if (itemList[i] === item) {
                        return i;
                    }
                }
                return 0;
            }; 
        }
        
        return DemoLogAnalyticsViewModel;
    });
