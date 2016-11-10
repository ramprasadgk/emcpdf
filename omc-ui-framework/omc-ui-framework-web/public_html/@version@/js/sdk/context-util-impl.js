define([
    'ojs/ojcore',
    'jquery',
    'uifwk/@version@/js/util/df-util-impl',
    'uifwk/@version@/js/util/usertenant-util-impl'
],
    function (oj, $, dfuModel, userTenantUtilModel)
    {
        function UIFWKContextUtil() {
            var self = this;
            var dfu = new dfuModel();
            var userTenantUtil = new userTenantUtilModel();
            var supportedContext = [{'contextName': 'time','paramNames': ['startTime', 'endTime', 'timePeriod']}, 
                                    {'contextName': 'composite','paramNames': ['compositeType', 'compositeName', 'compositeMEID']},
                                    {'contextName': 'entity','paramNames': ['entitiesType', /*'entityName',*/ 'entityMEIDs']}
                                   ];
            var omcCtxParamName = 'omcCtx';

            //Initialize window _uifwk object
            if (!window._uifwk) {
                window._uifwk = {};
            }

            /**
             * Get the OMC global context. This api will only return OMC conext, 
             * any page's private context from URL will be ignored. For any page 
             * to use oj_Router, if you want to get OMC context acccurately by 
             * this api during page loading, this api is expected to be called 
             * before any call to oj.Router.rootInstance.store(state) is called.
             * 
             * @returns {Object} OMC global context in json format
             */
            self.getOMCContext = function() {
                var omcContext = null;
                //If context already retrieved, fetch it from window object directly
                if (window._uifwk.omcContext) {
                    omcContext = window._uifwk.omcContext;
                }
                //Otherwise, retrieve the context from URL parameters
                if (!omcContext) {
                    omcContext = getContextFromUrl();
                }
                //If omcCOntext is missed from URL try to retrive it from sessionStorage
                if (!omcContext
                    && window.sessionStorage._uifwk_omcContext)
                {
                    omcContext = getContextFromSessionStorage();
                }
                //If omcContext not defined, use localStorage as last resource. This is for situation
                //like when opening a new tab.
                /*if (!omcContext &&
                    window.localStorage._uifwk_omcContext) {
                    omcContext = JSON.parse(window.localStorage._uifwk_omcContext);
                    self.setOMCContext(omcContext);
                }*/

                if (!omcContext) {
                    omcContext = {};
                    storeContext(omcContext);
                }
                
                oj.Logger.info("OMC gloable context is fetched as: " + JSON.stringify(omcContext));
                return omcContext;
            };

            function getContextFromSessionStorage() {
                var currentUserAndTenant = getUserAndTenant();
                var storedUserAndTenant = window.sessionStorage._uifwk_userAndTenant;
                var omcContext;
                if (currentUserAndTenant === storedUserAndTenant) {
                    omcContext = JSON.parse(window.sessionStorage._uifwk_omcContext);
                    self.setOMCContext(omcContext);
                }
                return omcContext;
            }

            function getUserAndTenant(){
                var tenantUser = userTenantUtil.getUserTenant();
                return tenantUser.user + '.' + tenantUser.tenant;
            }

            function getContextFromUrl() {
                var omcContext = {};
                var omcCtxString = decodeURIComponent(dfu.getUrlParam(omcCtxParamName));
                //Loop through supported context list
                for (var i = 0; i < supportedContext.length; i++) {
                    var contextDef = supportedContext[i];
                    var contextName = contextDef.contextName;
                    var contextParams = contextDef.paramNames;
                    //Loop through parameters for each context
                    for (var j = 0; j < contextParams.length; j++) {
                        var paramName = contextParams[j];
                        //Get param value form URL by name
                        var paramValue = retrieveParamValueFromUrl(omcCtxString, paramName);
                        if (paramValue) {
                            //Initialize
                            if (!omcContext[contextName]) {
                                omcContext[contextName] = {};
                            }
                            //Set value into the OMC context JSON object
                            omcContext[contextName][paramName] = paramValue;
                        }
                    }
                }
                if (!$.isEmptyObject(omcContext)) {
                    storeContext(omcContext);
                    return omcContext;
                }
                return null;
            }
            
            /**
             * Update the OMC global context. This function is used any the 
             * context is changed from within the page. For example, user changes
             * the time range, or selects a new entity to investigate.
             * 
             * @param {Object} context Context object in json format
             * @returns 
             */
            self.setOMCContext = function(context) {
                storeContext(context);
                updateCurrentURL();
                fireOMCContextChangeEvent();
            };
            
            function updateCurrentURL() {
                //update current URL
                var url = window.location.href.split('/').pop();
                url = self.appendOMCContext(url);
                var newurl=window.location.pathname.substring(0,window.location.pathname.lastIndexOf('/'));
                newurl=newurl+'/'+url;
                window.history.replaceState(window.history.state, document.title, newurl);
            }

            function storeContext(context) {
                //For now, we use window local variable to store the omc context once it's fetched from URL.
                //So even page owner rewrites the URL using oj_Router etc., the omc context will not be lost.
                //But need to make sure the omc context is initialized before page owner start to rewrites
                //the URL by oj_Router etc..
                window._uifwk.omcContext = context;
                //We use SessionStorage to help preserve the omc context during navigation, when
                //URL does not contains the omc context
                window.sessionStorage._uifwk_omcContext = JSON.stringify(context);
                var currentUserAndTenant = getUserAndTenant();
                window.sessionStorage._uifwk_userAndTenant = currentUserAndTenant;
                
                //We use localStorage as last resource to retrive the omc context. For cases
                //when user just have open the browser, or when opening a new tab to restore the
                //last used context,
                //window.localStorage._uifwk_omcContext = JSON.stringify(context);
            }
            
            /**
             * Get the current OMC global context and append it into the given 
             * URL as parameters. This function is used by custom deep linking 
             * code written by page. Where the page owner generates the destination 
             * but want to pass on the global context.
             * 
             * @param {String} url Original URL
             * @returns {String} New URL with appended OMC global context
             */
            self.appendOMCContext = function(url) {
                var newUrl = url;
                if (url) {
                    //Get OMC context
                    var omcContext = self.getOMCContext();
                    var omcCtxString = "";
                    if (omcContext) {
                        //Add or update URL parameters string for OMC context
                        for (var i = 0; i < supportedContext.length; i++) {
                            var contextDef = supportedContext[i];
                            var contextName = contextDef.contextName;
                            var contextParams = contextDef.paramNames;
                            //Loop through parameters for each context
                            for (var j = 0; j < contextParams.length; j++) {
                                var paramName = contextParams[j];
                                //Check for available context which should be appended into URL
                                if (omcContext[contextName] && omcContext[contextName][paramName]) {
                                    var paramValue = omcContext[contextName][paramName];
                                    omcCtxString = omcCtxString + encodeURIComponent(paramName) + "=" + encodeURIComponent(paramValue) + '&';
                                }
                            }
                        }
                        if (omcCtxString && omcCtxString.lastIndexOf('&') !== -1) {
                            omcCtxString = omcCtxString.substring(0, omcCtxString.lastIndexOf('&'));
                        }
                    }
                    //Retrieve omcCtx from original URL
                    var origOmcCtx = retrieveParamValueFromUrl(url, omcCtxParamName);
                    //If OMC context is not empty, append it to the URL, or if original URL has omcCtx already, then update it.
                    if (omcCtxString || origOmcCtx) {
                        //Add or update URL parameters
                        newUrl = addOrUpdateUrlParam(newUrl, omcCtxParamName, encodeURIComponent(omcCtxString));
                    }
                }
                else {
                    oj.Logger.error("Invalid empty URL input!");
                }
                
                return newUrl;
            };
            
            /**
             * Set OMC global context of start time.
             * 
             * @param {Number} startTime Start time
             * @returns 
             */
            self.setStartTime = function(startTime) {
                setIndividualContext('time', 'startTime', parseInt(startTime));
            };
            
            /**
             * Get OMC global context of start time.
             * 
             * @param 
             * @returns {Number} OMC global context of start time
             */
            self.getStartTime = function() {
                return parseInt(getIndividualContext('time', 'startTime'));
            };
            
            /**
             * Set OMC global context of end time.
             * 
             * @param {Number} endTime End time
             * @returns 
             */
            self.setEndTime = function(endTime) {
                setIndividualContext('time', 'endTime', parseInt(endTime));
            };
            
            /**
             * Get OMC global context of end time.
             * 
             * @param 
             * @returns {Number} OMC global context of end time
             */
            self.getEndTime = function() {
                return parseInt(getIndividualContext('time', 'endTime'));
            };
            
            /**
             * Set OMC global context of time period.
             * 
             * @param {String} timePeriod Time period like 'Last 1 Week' etc.
             * @returns 
             */
            self.setTimePeriod = function(timePeriod) {
                setIndividualContext('time', 'timePeriod', timePeriod);
            };
            
            /**
             * Get OMC global context of time period.
             * 
             * @param 
             * @returns {String} OMC global context of time period
             */
            self.getTimePeriod = function() {
                return getIndividualContext('time', 'timePeriod');
            };
            
            /**
             * Set OMC global context of composite guid.
             * 
             * @param {String} compositeMEID Composite GUID
             * @returns 
             */
            self.setCompositeMeId = function(compositeMEID) {
                setIndividualContext('composite', 'compositeMEID', compositeMEID, false);
                //Set composite meId will reset composite type/name, 
                //next time you get the composite type/name will return the new type/name
                setIndividualContext('composite', 'compositeType', null, false);
                setIndividualContext('composite', 'compositeName', null, false);
                setIndividualContext('composite', 'compositeDisplayName', null, false);
                setIndividualContext('composite', 'compositeNeedRefresh', true, false);
                fireOMCContextChangeEvent();
            };
            
            /**
             * Get OMC global context of composite guid.
             * 
             * @param 
             * @returns {String} OMC global context of composite guid
             */
            self.getCompositeMeId = function() {
                return getIndividualContext('composite', 'compositeMEID');
            };
            
//            /**
//             * Set OMC global context of composite type.
//             * 
//             * @param {String} compositeType Composite type
//             * @returns 
//             */
//            self.setCompositeType = function(compositeType) {
//                setIndividualContext('composite', 'compositeType', compositeType);
//            };
            
            /**
             * Get OMC global context of composite type.
             * 
             * @param 
             * @returns {String} OMC global context of composite type
             */
            self.getCompositeType = function() {
                var compositeType = getIndividualContext('composite', 'compositeType');
                if (compositeType) {
                    return compositeType;
                }
                else if (self.getCompositeMeId() && getIndividualContext('composite', 'compositeNeedRefresh') !== 'false') {
                    //Fetch composite name/type
                    queryODSEntitiesByMeIds([self.getCompositeMeId()], fetchCompositeCallback);
                }
                return getIndividualContext('composite', 'compositeType');
            };
            
//            /**
//             * Set OMC global context of composite name.
//             * 
//             * @param {String} compositeName Composite name
//             * @returns 
//             */
//            self.setCompositeName = function(compositeName) {
//                setIndividualContext('composite', 'compositeName', compositeName);
//            };
            
            /**
             * Get OMC global context of composite internal name.
             * 
             * @param 
             * @returns {String} OMC global context of composite internal name
             */
            self.getCompositeName = function() {
                var compositeName = getIndividualContext('composite', 'compositeName');
                if (compositeName) {
                    return compositeName;
                }
                else if (self.getCompositeMeId() && getIndividualContext('composite', 'compositeNeedRefresh') !== 'false') {
                    //Fetch composite name/type
                    queryODSEntitiesByMeIds([self.getCompositeMeId()], fetchCompositeCallback);
                }
                return getIndividualContext('composite', 'compositeName');
            };
            
            /**
             * Get OMC global context of composite display name.
             * 
             * @param 
             * @returns {String} OMC global context of composite display name
             */
            self.getCompositeDisplayName = function() {
                var compositeDisplayName = getIndividualContext('composite', 'compositeDisplayName');
                if (compositeDisplayName) {
                    return compositeDisplayName;
                }
                else if (self.getCompositeMeId() && getIndividualContext('composite', 'compositeNeedRefresh') !== 'false') {
                    //Fetch composite name/type
                    queryODSEntitiesByMeIds([self.getCompositeMeId()], fetchCompositeCallback);
                }
                //In case composite type+name are passed in from URL, return composite name for now
                //Need to enhance the logic to query composite by type+name in next steps
                else if (self.getCompositeType() && self.getCompositeName()) {
                    return self.getCompositeName();
                }
                return getIndividualContext('composite', 'compositeDisplayName');
            };
            
            /**
             * Get composite class.
             * 
             * @param 
             * @returns {String} Composite class
             */
            self.getCompositeClass = function() {
                var compositeClass = getIndividualContext('composite', 'compositeClass');
                if (compositeClass) {
                    return compositeClass;
                }
                else if (self.getCompositeMeId() && getIndividualContext('composite', 'compositeNeedRefresh') !== 'false') {
                    //Fetch composite name/type
                    queryODSEntitiesByMeIds([self.getCompositeMeId()], fetchCompositeCallback);
                }
                return getIndividualContext('composite', 'compositeClass');
            };
            
//            /**
//             * Set OMC global context of entity guid.
//             * 
//             * @param {String} entityMEID Entity GUID
//             * @returns 
//             */
//            self.setEntityMeId = function(entityMEID) {
//                setIndividualContext('entity', 'entityMEID', entityMEID);
//            };
//            
//            /**
//             * Get OMC global context of entity guid.
//             * 
//             * @param 
//             * @returns {String} OMC global context of entity guid
//             */
//            self.getEntityMeId = function() {
//                return getIndividualContext('entity', 'entityMEID');
//            };
            
            /**
             * Set OMC global context of multiple entity GUIDs.
             * 
             * @param {Array} entityMEIDs A list of Entity GUIDs
             * @returns 
             */
            self.setEntityMeIds = function(entityMEIDs) {
                var meIds = '';
                //If it's a array, convert it to a comma seperated string
                if ($.isArray(entityMEIDs)) {
                    for (var i = 0; i < entityMEIDs.length; i++) {
                        if (i === entityMEIDs.length - 1) {
                            meIds = meIds + entityMEIDs[i];
                        }
                        else {
                            meIds = meIds + entityMEIDs[i] + ',';
                        }
                    }
                }
                else {
                    meIds = entityMEIDs;
                }
                setIndividualContext('entity', 'entityMEIDs', meIds);
                //Set entity meIds will reset the cached entity objects, 
                //next time you get the entities will return the new ones
                setIndividualContext('entity', 'entities', null);
            };
            
            /**
             * Get OMC global context of entity MEIDs.
             * 
             * @param 
             * @returns {Array} OMC global context of entity MEIDs
             */
            self.getEntityMeIds = function() {
                var strMeIds = getIndividualContext('entity', 'entityMEIDs');
                if (strMeIds) {
                    //Convert to a array
                    return strMeIds.split(',');
                }
                return null;
            };
            
            /**
             * Set OMC global context of entities type.
             * 
             * @param {String} entitiesType Entities type
             * @returns 
             */
            self.setEntitiesType = function(entitiesType) {
                setIndividualContext('entity', 'entitiesType', entitiesType);
            };
            
            /**
             * Get OMC global context of entities type.
             * 
             * @param 
             * @returns {String} OMC global context of entities type
             */
            self.getEntitiesType = function() {
                return getIndividualContext('entity', 'entitiesType');
            };
            
//            /**
//             * Set OMC global context of entity name.
//             * 
//             * @param {String} entityName Entity name
//             * @returns 
//             */
//            self.setEntityName = function(entityName) {
//                setIndividualContext('entity', 'entityName', entityName);
//            };
            
//            /**
//             * Get OMC global context of entity name.
//             * 
//             * @param 
//             * @returns {String} OMC global context of entity name
//             */
//            self.getEntityName = function() {
//                return getIndividualContext('entity', 'entityName');
//            };
            
            /**
             * Clear OMC global composite context.
             * 
             * @param 
             * @returns 
             */
            self.clearCompositeContext = function() {
                clearIndividualContext('composite');
            };
            
            /**
             * Clear OMC global time context.
             * 
             * @param 
             * @returns 
             */
            self.clearTimeContext = function() {
                clearIndividualContext('time');
            };
            
            /**
             * Clear OMC global entity context.
             * 
             * @param 
             * @returns 
             */
            self.clearEntityContext = function() {
                clearIndividualContext('entity');
            };
            
            /**
             * Get a list of entity objects by entity MEIDs.
             * 
             * @param 
             * @returns {Object} a list of entity objects
             */
            self.getEntities = function() {
                var entities = getIndividualContext('entity', 'entities');
                if (entities && $.isArray(entities) && entities.length > 0) {
                    return entities;
                }
                else {
                    var entityMeIds = self.getEntityMeIds();
                    var entitiesType = self.getEntitiesType();
                    entities = [];
                    if (entityMeIds && entityMeIds.length > 0 && entitiesType) {
                        //Query entities by meIds and filter by entites type
                        queryODSEntitiesByMeIds(entityMeIds, loadEntities);
                        for (var i = 0; i < entitiesFetched.length; i ++) {
                            var entity = entitiesFetched[i];
                            if (entity['entityType'] === entitiesType) {
                                entities.push(entity);
                            }
                        }
                    }
                    else if (entityMeIds && entityMeIds.length > 0) {
                        //Query entities by meIds
                        queryODSEntitiesByMeIds(entityMeIds, loadEntities);
                        for (var i = 0; i < entitiesFetched.length; i ++) {
                            entities.push(entitiesFetched[i]);
                        }
                    }
                    else if (entitiesType) {
                        //Query by entities type
                        queryODSEntitiesByEntityType(entitiesType, loadEntities);
                        for (var i = 0; i < entitiesFetched.length; i ++) {
                            entities.push(entitiesFetched[i]);
                        }
                    }
                    
                    //Cache the entities data
                    var omcCtx = self.getOMCContext();
                    if (!omcCtx['entity']) {
                        omcCtx['entity'] = {};
                    }
                    omcCtx['entity']['entities'] = entities;
                    storeContext(omcCtx);
                    return entities;
                }
            };
            
            /**
             * Fire OMC change event when omc context is updated.
             * 
             * @param {Object} currentCtx Current OMC context
             * @returns 
             */            
            function fireOMCContextChangeEvent(currentCtx) {
                var message = {'tag': 'EMAAS_OMC_GLOBAL_CONTEXT_UPDATED', 'currentCtx': currentCtx};
                window.postMessage(message, window.location.href);
            }
            
            /**
             * Clear individual OMC global context.
             * 
             * @param {String} contextName Context definition name
             * @returns 
             */
            function clearIndividualContext(contextName) {
                if (contextName) {
                    var omcContext = self.getOMCContext();
                    if (omcContext[contextName]) {
                        delete omcContext[contextName];
                        storeContext(omcContext);
                        updateCurrentURL();
                        fireOMCContextChangeEvent();
                    }
                }
            }
            
            /**
             * Set individual OMC global context.
             * 
             * @param {String} contextName Context definition name
             * @param {String} paramName URL parameter name for the individual context
             * @param {Boolean} fireChangeEvent Flag to determine whether to fire change event
             * @param {String} value Context value
             * @returns 
             */
            function setIndividualContext(contextName, paramName, value, fireChangeEvent) {
                if (contextName && paramName) {
                    var omcContext = self.getOMCContext();
                    //If value is not null and not empty
                    if (value) {
                        if (!omcContext[contextName]) {
                            omcContext[contextName] = {};
                        }
                        omcContext[contextName][paramName] = decodeURIComponent(value);
                    }
                    //Otherwise, if value is null or empty then clear the context
                    else if (omcContext[contextName] && omcContext[contextName][paramName]) {
                        delete omcContext[contextName][paramName];
                    }
                    storeContext(omcContext);
                    updateCurrentURL();
                    if (fireChangeEvent !== false) {
                        fireOMCContextChangeEvent();
                    }
                }
            }
            
            /**
             * Get individual OMC global context.
             * 
             * @param {String} contextName Context definition name
             * @param {String} paramName URL parameter name for the individual context
             * @returns {String} Individual OMC global context
             */
            function getIndividualContext(contextName, paramName) {
                if (contextName && paramName) {
                    var omcContext = self.getOMCContext();
                    if (omcContext[contextName] && omcContext[contextName][paramName]) {
                        return omcContext[contextName][paramName];
                    }
                }
                return null;
            }
            
            /**
             * Add new parameter into the URL if it doesn't exist in original URL.
             * Otherwise, update the parameter in the URL if it exists already.
             * 
             * @param {String} url Original URL
             * @param {String} paramName Parameter name
             * @param {String} paramValue Parameter value
             * @returns {String} New URL
             */
            function addOrUpdateUrlParam(url, paramName, paramValue){
                if (paramValue === null) {
                    paramValue = '';
                }
                var pattern = new RegExp('([?&])' + paramName + '=.*?(&|$)', 'i');
                if (url.match(pattern)) {
                  return url.replace(pattern, '$1' + paramName + "=" + paramValue + '$2');
                }
                return url + (url.indexOf('?') > 0 ? 
                    //Handle case that an URL ending with a question mark only
                    (url.lastIndexOf('?') === url.length - 1 ? '': '&') : '?') + paramName + '=' + paramValue; 
            };
            
            /**
             * Retrieve parameter value from given URL string.
             * 
             * @param {String} decodedUrl Decoded URL string
             * @param {String} paramName Parameter name
             * @returns {String} Parameter value
             */
            function retrieveParamValueFromUrl(decodedUrl, paramName) {
                if (decodedUrl && paramName) {
                    if (decodedUrl.indexOf('?') !== 0) {
                        decodedUrl = '?' + decodedUrl;
                    }
                    var regex = new RegExp("[\\?&]" + encodeURIComponent(paramName) + "=([^&#]*)"), results = regex.exec(decodedUrl);
                    return results === null ? null : decodeURIComponent(results[1]);
                }
                return null;
            };
            
            var entitiesFetched = [];
            function loadEntities(data) {
                entitiesFetched = [];
                if (data && data['rows']) {
                    var dataRows = data['rows'];
                    for (var i = 0; i < dataRows.length; i++) {
                        var entity = {};
                        entity['meId'] = dataRows[i][0];
                        entity['displayName'] = dataRows[i][1];
                        entity['entityName'] = dataRows[i][2];
                        entity['entityType'] = dataRows[i][4];
                        entity['meClass'] = dataRows[i][5];
                        entitiesFetched.push(entity);
                    }
                }
            }
            
            function queryODSEntitiesByMeIds(meIds, callback) {
                if (meIds && meIds.length > 0) {
                    var jsonOdsQuery = {
                        "ast": {"query": "simple",
                            "select": [{"item": {"expr": "column","table": "me","column": "meId"},"alias": "s1"}, 
                                {"item": {"expr": "column","table": "me","column": "displayName"},"alias": "s2"}, 
                                {"item": {"expr": "column","table": "me","column": "entityName"},"alias": "s3"}, 
                                {"item": {"expr": "function","name": "NVL","args": [{"expr": "column","table": "tp1","column": "typeDisplayName"}, 
                                            {"expr": "column","table": "me","column": "entityType"}]}, "alias": "s4"}, 
                                {"item": {"expr": "column","table": "me","column": "entityType"},"alias": "s5"},
                                {"item": {"expr": "column","table": "tp1","column": "meClass"},"alias": "s6"}],
                            "distinct": true,
                            "from": [{
                                "table": "innerJoin",
                                "lhs": {"table": "virtual","name": "Target","alias": "me"},
                                "rhs": {"table": "virtual","name": "ManageableEntityType","alias": "tp1"},
                                "on": {
                                    "cond": "compare",
                                    "comparator": "EQ",
                                    "lhs": {"expr": "column","table": "me","column": "entityType"},
                                    "rhs": {"expr": "column","table": "tp1","column": "entityType"}
                                }
                            }],
                            "where": {
                                "cond": "inExpr",
                                "not": false,
                                "lhs": {"expr": "column","table": "me","column": "meId"},
                                "rhs": []
                            },
                            "orderBy": {
                                "entries": [{
                                    "entry": "expr",
                                    "item": {"expr": "function","name": "UPPER","args": [{"expr": "column","table": "me","column": "entityName"}]},
                                    "direction": "DESC",
                                    "nulls": "LAST"
                                }]
                            },
                            "groupBy": null
                        }
                    };
                    
                    for (var i = 0; i < meIds.length; i++) {
                        jsonOdsQuery['ast']['where']['rhs'][i] = {};
                        jsonOdsQuery['ast']['where']['rhs'][i]['expr'] = 'str'; 
                        jsonOdsQuery['ast']['where']['rhs'][i]['val'] = meIds[i]; 
                    }
                    oj.Logger.info("Start to get ODS entities by entity MEIDs.", false);
                    executeODSQuery(jsonOdsQuery, callback);
                }
            }
            
            function queryODSEntitiesByEntityType(entityType, callback) {
                if (entityType) {
                    var jsonOdsQuery = {
                        "ast": {"query": "simple",
                            "select": [{"item": {"expr": "column","table": "me","column": "meId"},"alias": "s1"}, 
                                {"item": {"expr": "column","table": "me","column": "displayName"},"alias": "s2"}, 
                                {"item": {"expr": "column","table": "me","column": "entityName"},"alias": "s3"}, 
                                {"item": {"expr": "function","name": "NVL","args": [{"expr": "column","table": "tp1","column": "typeDisplayName"}, 
                                            {"expr": "column","table": "me","column": "entityType"}]}, "alias": "s4"}, 
                                {"item": {"expr": "column","table": "me","column": "entityType"},"alias": "s5"},
                                {"item": {"expr": "column","table": "tp1","column": "meClass"},"alias": "s6"}],
                            "distinct": true,
                            "from": [{
                                "table": "innerJoin",
                                "lhs": {"table": "virtual","name": "Target","alias": "me"},
                                "rhs": {"table": "virtual","name": "ManageableEntityType","alias": "tp1"},
                                "on": {
                                    "cond": "compare",
                                    "comparator": "EQ",
                                    "lhs": {"expr": "column","table": "me","column": "entityType"},
                                    "rhs": {"expr": "column","table": "tp1","column": "entityType"}
                                }
                            }],
                            "where": {"cond": "compare","comparator": "EQ",
                                "lhs": {"expr": "column","table": "me","column": "entityType"},
                                "rhs": {'expr': 'str', 'val': entityType}
                            },
                            "orderBy": {
                                "entries": [{
                                    "entry": "expr",
                                    "item": {"expr": "function","name": "UPPER","args": [{"expr": "column","table": "me","column": "entityName"}]},
                                    "direction": "DESC",
                                    "nulls": "LAST"
                                }]
                            },
                            "groupBy": null
                        }
                    };
                    
                    oj.Logger.info("Start to get ODS entities by entity type.", false);
                    executeODSQuery(jsonOdsQuery, callback);
                }
            }
            
            function fetchCompositeCallback(data) {
                if (data && data['rows'] && data['rows'].length > 0) {
                    var entity = data['rows'][0];
                    setIndividualContext('composite', 'compositeDisplayName', entity[1], false);
                    setIndividualContext('composite', 'compositeName', entity[2], false);
                    setIndividualContext('composite', 'compositeType', entity[4], false);
                    setIndividualContext('composite', 'compositeClass', entity[5], false);
                }
                else {
                    setIndividualContext('composite', 'compositeDisplayName', null, false);
                    setIndividualContext('composite', 'compositeName', null, false);
                    setIndividualContext('composite', 'compositeType', null, false);
                    setIndividualContext('composite', 'compositeClass', null, false);
                }
                setIndividualContext('composite', 'compositeNeedRefresh', 'false', false);
                fireOMCContextChangeEvent();
            }
            
            function executeODSQuery(jsonOdsQuery, callback) {
                var odsQueryUrl = getODSEntityQueryUrl();
                oj.Logger.info("Start to execute ODS query by URL:" + odsQueryUrl, false);
                dfu.ajaxWithRetry(odsQueryUrl,{
                    type: 'POST',
                    async: false,
                    data: JSON.stringify(jsonOdsQuery),
                    contentType: 'application/json',
                    headers: dfu.getHeadersForService(),
                    success:function(data, textStatus,jqXHR) {
                        callback(data);
                    },
                    error:function(xhr, textStatus, errorThrown){
                        oj.Logger.error("ODS query failed due to error: " + textStatus);
                        callback(null);
                    }
                });
            }

            function getODSEntityQueryUrl() {
                var odsUrl = '/sso.static/datamodel-query';
                if (dfu.isDevMode()){
                    odsUrl = dfu.getTargetModelServiceInDEVMode();
                    odsUrl = dfu.buildFullUrl(odsUrl, "query");
                }
                return odsUrl;
            }
        }

        return UIFWKContextUtil;
    }
);

