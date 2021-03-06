
define(['knockout',
        'jquery',
        'ojs/ojcore',
        'dfutil',
        'uifwk/js/sdk/menu-util'
    ],
    function(ko, $, oj, dfu, menuModel) {
        function EditDashboardDialogModel($b, tbModel) {
            var dsb = $b.dashboard;
            var self = this;
            var menuUtil = new menuModel();
            self.dashboard = dsb;
            self.tbModel = tbModel;
            self.name = ko.observable(dsb.name());
            self.nameInputed = ko.observable(undefined); //read only input text
            self.nameFocused = ko.observable(false);
            self.descriptionFocused = ko.observable(false);
            self.description = ko.observable(dsb.description ? dsb.description() : undefined);
            self.descriptionInputed = ko.observable(undefined);
            self.descriptionValue = ko.observable(dsb.enableDescription ? (dsb.enableDescription()==="TRUE"?["ON"]:["OFF"]) : ["OFF"]);

            var isEditDsbOptionEnabled = function(optionVal) {
                if(optionVal() === "ON" ||
                        optionVal()[0] === "ON") {
                    return true;
                }
                return false;
            };
            self.showdbDescription = ko.observable(isEditDsbOptionEnabled(self.descriptionValue) ?["showdbDescription"]:[]);

            self.entityFilterValue = ko.observable(dsb.enableEntityFilter ? (dsb.enableEntityFilter()==="TRUE"?["ON"]:["OFF"]) : ["OFF"]);
            self.timeRangeFilterValue = ko.observable(dsb.enableTimeRange ? (dsb.enableTimeRange()==="TRUE"?["ON"]:["OFF"]) : ["ON"]);
            self.nameValidated = ko.observable(true);
            self.isDisabled = ko.computed(function() {
                if (self.nameInputed() && self.nameInputed().length > 0)
                {
                    return false;
                }
                return true;
            });
            
            self.resetEmptyDashboardName = function(){
                if(!self.nameInputed() || !self.nameInputed().trim()){
                    self.tbModel && self.tbModel.dashboardName(dsb.name());
                    self.name(dsb.name());
                }
            };

            if (self.tbModel) {
                self.nameInputed.subscribe(function (val) {
                    self.tbModel && self.tbModel.dashboardName(val);
                    self.name(val);
                });
                self.tbModel && self.tbModel.dashboardDescription.subscribe(function (val) {
                    self.dashboard.description && self.dashboard.description(val);
                    if (!self.tbModel.dashboardDescription()) {
                        self.showdbDescription([]);
                    } else {
                        self.showdbDescription(["showdbDescription"]);
                    }
                });

                self.showdbDescription.subscribe(function (val) {
                    if (val.indexOf("showdbDescription") >= 0) {
                        self.descriptionValue("ON");
                        self.tbModel && self.tbModel.dashboardDescriptionEnabled("TRUE") && self.dashboard.enableDescription("TRUE");
                    } else {
                        self.descriptionValue("OFF");
                        self.tbModel && self.tbModel.dashboardDescriptionEnabled("FALSE") && self.dashboard.enableDescription("FALSE");
                    }
                });

                self.descriptionInputed.subscribe(function (val) {
                    if (self.dashboard.description) {
                        self.dashboard.description(val);
                    } else {
                        self.dashboard.description = ko.observable(val);
                    }
                    self.dashboard.description(val);
                    self.tbModel.dashboardDescription(val);
                    self.description(val);
                });
            }




            self.noSameNameValidator = {
                    'validate' : function (value) {
                        self.nameValidated(true);
                        value = value + "";

                        if (value && Builder.isDashboardNameExisting(value)) {
                            self.nameValidated(false);
                            throw new oj.ValidatorError(oj.Translations.getTranslatedString("COMMON_DASHBAORD_SAME_NAME_ERROR"),
                                             oj.Translations.getTranslatedString("COMMON_DASHBAORD_SAME_NAME_ERROR_DETAIL"));
                        }
                        return true;
                    }
            };

            self.save = function() {
                if (self.nameValidated() === false || dfu.getUserName()!==self.dashboard.owner()){
                    return;
                }
                
                var successCallback = function(result) {
                    self.dashboard.name(self.name());
                    self.tbModel && self.tbModel.dashboardName(self.name());
                    if (self.dashboard.description) {
                        self.dashboard.description(self.description());
                    }else {
                        self.dashboard.description = ko.observable(self.description());
                    }
                    if (self.errSavindMsgId) {
                        dfu.removeMessage(self.errSavindMsgId);
                    }
                    $('#edit-dashboard').ojDialog("close");
                    //Fire event to refresh "Federated dashboard" and "Favorite dashabord" in HM
                    menuUtil.fireFederatedDsbChangedEvent();
                    menuUtil.fireFavoriteDsbChangedEvent();
                };
                var errorCallback = function(jqXHR, textStatus, errorThrown) {
                    if (self.errSavindMsgId) {
                        dfu.removeMessage(self.errSavindMsgId);
                    }
                    if (jqXHR && jqXHR[0] && jqXHR[0].responseJSON && jqXHR[0].responseJSON.errorCode === 10001)
                    {
                        _m = getNlsString('COMMON_DASHBAORD_SAME_NAME_ERROR');
                        _mdetail = getNlsString('COMMON_DASHBAORD_SAME_NAME_ERROR_DETAIL');
                        self.errSavindMsgId = dfu.showMessage({type: 'error', summary: _m, detail: _mdetail});
                    }else if (jqXHR && jqXHR.responseJSON && jqXHR.responseJSON.errorCode === 10001)
                    {
                        _m = getNlsString('COMMON_DASHBAORD_SAME_NAME_ERROR');
                        _mdetail = getNlsString('COMMON_DASHBAORD_SAME_NAME_ERROR_DETAIL');
                        self.errSavindMsgId = dfu.showMessage({type: 'error', summary: _m, detail: _mdetail});
                    }else if(ko.unwrap(jqXHR.errorCode) === 10001) {
                        _m = getNlsString('COMMON_DASHBAORD_SAME_NAME_ERROR');
                        _mdetail = getNlsString('COMMON_DASHBAORD_SAME_NAME_ERROR_DETAIL');
                        self.errSavindMsgId = dfu.showMessage({type: 'error', summary: _m, detail: _mdetail});
                    }
                    else
                    {
                        // a server error record
                        oj.Logger.error("Error when creating dashboard. " + (jqXHR ? jqXHR.responseText : ""));
                        self.errSavindMsgId = dfu.showMessage({type: 'error', summary: getNlsString('DBS_BUILDER_MSG_ERROR_IN_SAVING'), detail: ''});
                    }
                };
                
                var fieldsToUpdate = {
                    name: self.name(),
                    description: self.description(),
                    enableDescription: isEditDsbOptionEnabled(self.descriptionValue) ? "TRUE" : "FALSE",
                    enableEntityFilter: (isEditDsbOptionEnabled(self.entityFilterValue) ? "TRUE" : "FALSE"),
                    enableTimeRange: (isEditDsbOptionEnabled(self.timeRangeFilterValue) ? "TRUE" : "FALSE")
                };
                var newDasboardJs = ko.mapping.toJS(self.dashboard, {
                    'include': ['screenShot', 'description', 'height',
                        'isMaximized', 'title', 'type', 'width',
                        'tileParameters', 'name', 'systemParameter',
                        'tileId', 'value', 'content', 'linkText',
                        'WIDGET_LINKED_DASHBOARD', 'linkUrl'],
                    'ignore': ["createdOn", "href", "owner", "modeWidth", "modeHeight",
                        "modeColumn", "modeRow", "screenShotHref", "systemDashboard",
                        "customParameters", "clientGuid", "dashboard",
                        "fireDashboardItemChangeEvent", "getParameter",
                        "maximizeEnabled", "narrowerEnabled",
                        "onDashboardItemChangeEvent", "restoreEnabled",
                        "setParameter", "shouldHide", "systemParameters",
                        "tileDisplayClass", "widerEnabled", "widget",
                        "WIDGET_DEFAULT_HEIGHT", "WIDGET_DEFAULT_WIDTH"]
                });
                $.extend(newDasboardJs, fieldsToUpdate);
                new Builder.DashboardDataSource().updateDashboardData(self.dashboard.id(), JSON.stringify(newDasboardJs), successCallback, errorCallback);
            };

            self.clear = function() {
                self.name(self.dashboard.name());
                self.description(self.dashboard.description ? self.dashboard.description() : undefined);
            };


        }

        return {"EditDashboardDialogModel": EditDashboardDialogModel};
    }
);
