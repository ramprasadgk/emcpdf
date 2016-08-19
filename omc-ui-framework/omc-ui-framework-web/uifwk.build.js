 ({
    appDir: "../build/staging/public_html/",
    baseUrl: ".",
    dir: "../build/public_html",
    optimize:"none",
    optimizeCss: "standard",
    modules: [
        {
            name: "uifwk/@version@/js/uifwk-impl-partition",
            exclude: ["knockout", 
                "jquery",
                "ojs/ojcore",
                "ojs/ojdialog", 
                "ojs/ojbutton",
                "ojs/ojknockout", 
                "ojs/ojtoolbar", 
                "ojs/ojmenu",
                "ojs/ojdatetimepicker",
                "ojs/ojcheckboxset",
                "ojs/ojselectcombobox", 
                "ojs/ojinputtext",
                "text",
                "ojL10n",
                "ojL10n!uifwk/@version@/js/resources/nls/uifwkCommonMsg",
                "uifwk/libs/@version@/js/html2canvas/html2canvas",
                "uifwk/libs/@version@/js/canvg/rgbcolor",
                "uifwk/libs/@version@/js/canvg/StackBlur",
                "uifwk/libs/@version@/js/canvg/canvg"
            ]
        },
        {
            name: "uifwk/js/uifwk-partition",
            exclude: ["uifwk/@version@/js/uifwk-impl-partition","knockout", 
                "jquery",
                "ojs/ojcore",
                "ojs/ojdialog", 
                "ojs/ojbutton",
                "ojs/ojknockout", 
                "ojs/ojtoolbar", 
                "ojs/ojmenu",
                "ojs/ojdatetimepicker",
                "ojs/ojcheckboxset",
                "ojs/ojselectcombobox", 
                "ojs/ojinputtext",
                "text",
                "ojL10n",
                "ojL10n!uifwk/@version@/js/resources/nls/uifwkCommonMsg",
                "uifwk/libs/@version@/js/html2canvas/html2canvas",
                "uifwk/libs/@version@/js/canvg/rgbcolor",
                "uifwk/libs/@version@/js/canvg/StackBlur",
                "uifwk/libs/@version@/js/canvg/canvg"]
        }
    ],
    paths: {
        'uifwk/js/uifwk-partition': 'js/uifwk-partition',
        'uifwk/@version@/js/uifwk-impl-partition': '@version@/js/uifwk-impl-partition',
    	'knockout': 'libs/@version@/js/oraclejet/js/libs/knockout/knockout-3.4.0',
        'knockout.mapping': 'libs/@version@/js/oraclejet/js/libs/knockout/knockout.mapping-latest',
        'jquery': 'libs/@version@/js/oraclejet/js/libs/jquery/jquery-2.1.3.min',
        'jqueryui': 'libs/@version@/js/jquery/jquery-ui-1.11.4.custom.min',
        'jqueryui-amd':'libs/@version@/js/oraclejet/js/libs/jquery/jqueryui-amd-1.11.4.min',
        'hammerjs': 'libs/@version@/js/oraclejet/js/libs/hammer/hammer-2.0.4.min',
        'ojs': 'libs/@version@/js/oraclejet/js/libs/oj/v2.0.2/min',
        'ojL10n': 'libs/@version@/js/oraclejet/js/libs/oj/v2.0.2/ojL10n',
        'ojtranslations': 'libs/@version@/js/oraclejet/js/libs/oj/v2.0.2/resources',
        'ojdnd': 'libs/@version@/js/oraclejet/js/libs/dnd-polyfill/dnd-polyfill-1.0.0.min',
        'signals': 'libs/@version@/js/oraclejet/js/libs/js-signals/signals.min',
        'crossroads': 'libs/@version@/js/oraclejet/js/libs/crossroads/crossroads.min',
        'history': 'libs/@version@/js/oraclejet/js/libs/history/history.iegte8.min',
        'text': 'libs/@version@/js/oraclejet/js/libs/require/text',
        'promise': 'libs/@version@/js/oraclejet/js/libs/es6-promise/promise-1.0.0.min',
	'uifwk':'.',
        'require':'libs/@version@/js/oraclejet/js/libs/require/require'
    }
})
