<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %><%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %><%@ taglib uri="http://www.zkoss.org/dsp/web/theme" prefix="t" %>@media print{.z-borderlayout{<c:if test="${ zk.safari > 0 }">position:static;</c:if>}}@media screen{.z-borderlayout{position:relative}}.z-borderlayout{width:100%;height:100%;border:0;background:#fff;overflow:hidden}.z-borderlayout-icon{font-size:14px;color:#636363;display:block;width:24px;height:24px;line-height:24px;opacity:.5;filter:alpha(opacity=50);text-align:center;position:absolute;right:3px;overflow:hidden;cursor:pointer}.z-borderlayout-icon:hover{opacity:1;filter:alpha(opacity=100)}.z-north,.z-south,.z-west,.z-center,.z-east{border:1px solid #cfcfcf;background:#fff;position:absolute;overflow:hidden}.z-north-noborder,.z-south-noborder,.z-west-noborder,.z-center-noborder,.z-east-noborder{border:0}.z-north-header,.z-south-header,.z-west-header,.z-center-header,.z-east-header{font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;font-size:12px;font-weight:bold;font-style:normal;color:#555;height:32px;border-bottom:1px solid #cfcfcf;padding:4px 4px 3px;line-height:24px;${t:gradient('ver','#fefefe 0%; #eeeeee 100%') };overflow:hidden;cursor:default;white-space:nowrap}.z-north-body,.z-south-body,.z-west-body,.z-center-body,.z-east-body{line-height:14px}.z-north-collapsed,.z-south-collapsed,.z-west-collapsed,.z-center-collapsed,.z-east-collapsed{width:32px;height:32px;border:1px solid #cfcfcf;padding:5px;background:#fcfcfc;position:absolute;overflow:hidden;cursor:pointer}.z-north-caption,.z-south-caption,.z-west-caption,.z-center-caption,.z-east-caption{height:32px}.z-north,.z-south,.z-center{width:100%}.z-west,.z-east{height:100%}.z-west,.z-west-collapsed,.z-west-splitter{z-index:12}.z-center{z-index:8}.z-east,.z-east-collapsed,.z-east-splitter{z-index:10}.z-north,.z-north-collapsed,.z-north-splitter{z-index:16}.z-south,.z-south-collapsed,.z-south-splitter{z-index:14}.z-east-splitter,.z-west-splitter,.z-north-splitter,.z-south-splitter{width:8px;height:8px;${t:gradient('hor','#fdfdfd 0%; #f1f1f1 100%') };position:absolute;overflow:hidden;cursor:e-resize}.z-east-splitter-button,.z-west-splitter-button,.z-north-splitter-button,.z-south-splitter-button{color:#636363;display:inline-block;border:1px solid #cfcfcf;vertical-align:top;position:relative;cursor:pointer}.z-east-splitter-button-disabled,.z-west-splitter-button-disabled,.z-north-splitter-button-disabled,.z-south-splitter-button-disabled{border:0}.z-east-icon,.z-west-icon,.z-north-icon,.z-south-icon{font-size:10px;line-height:14px;opacity:.5;filter:alpha(opacity=50);position:absolute}.z-north-splitter,.z-south-splitter{border-left:1px solid #cfcfcf;border-right:1px solid #cfcfcf;${t:gradient('ver','#fdfdfd 0%; #f1f1f1 100%') };cursor:s-resize}<c:if test="${ zk.ie == 8 }">.z-west-icon.z-icon-caret-left,.z-south-icon.z-icon-caret-down,.z-east-icon.z-icon-caret-right,.z-north-icon.z-icon-caret-up{font-size:12px}.z-south-icon.z-icon-caret-down{top:-2px}</c:if>.z-north-splitter-button:hover .z-icon-caret-up,.z-south-splitter-button:hover .z-icon-caret-down,.z-west-splitter-button:hover .z-icon-caret-left,.z-east-splitter-button:hover .z-icon-caret-right{opacity:1}.z-north-splitter-button-disabled .z-icon-caret-up,.z-south-splitter-button-disabled .z-icon-caret-down,.z-west-splitter-button-disabled .z-icon-caret-left,.z-east-splitter-button-disabled .z-icon-caret-right{display:none}.z-north-splitter-button-disabled,.z-south-splitter-button-disabled{cursor:s-resize}.z-west-splitter-button-disabled,.z-east-splitter-button-disabled{cursor:e-resize}.z-west-icon,.z-east-icon{font-size:11px;top:8px;left:2px}.z-west-icon.z-icon-ellipsis-vertical,.z-east-icon.z-icon-ellipsis-vertical{font-size:10px;top:-21px;left:3px;cursor:e-resize;<c:if test="${ zk.ie == 8 }">font-size:14px;</c:if>}.z-north-icon,.z-south-icon{left:11px;top:-3px}.z-west-icon.z-icon-ellipsis-vertical ~ .z-west-icon.z-icon-ellipsis-vertical,.z-east-icon.z-icon-ellipsis-vertical ~ .z-east-icon.z-icon-ellipsis-vertical{top:39px}.z-north-icon.z-icon-ellipsis-horizontal,.z-south-icon.z-icon-ellipsis-horizontal{top:-2px;left:-20px;cursor:s-resize;<c:if test="${ zk.ie == 8 }">font-size:14px;</c:if>}.z-north-icon.z-icon-ellipsis-horizontal ~ .z-north-icon.z-icon-ellipsis-horizontal,.z-south-icon.z-icon-ellipsis-horizontal ~ .z-south-icon.z-icon-ellipsis-horizontal{left:40px}.z-west-splitter-button,.z-east-splitter-button{width:8px;height:30px;border-width:1px 0}.z-north-splitter-button,.z-south-splitter-button{width:30px;height:8px;border-width:0 1px}.ie8 .z-borderlayout>div{border:0}