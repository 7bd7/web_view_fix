(window.webpackJsonp=window.webpackJsonp||[]).push([[3],{1222:function(t,e,n){"use strict";n.r(e);var o=n(1229),r=n(1223);for(var l in r)"default"!==l&&function(t){n.d(e,t,(function(){return r[t]}))}(l);var c=n(1227),d=n(0);var component=Object(d.a)(r.default,o.a,o.b,!1,(function(t){this.$style=c.default.locals||c.default}),null,null);e.default=component.exports},1223:function(t,e,n){"use strict";n.r(e);var o=n(1224),r=n.n(o);for(var l in o)"default"!==l&&function(t){n.d(e,t,(function(){return o[t]}))}(l);e.default=r.a},1224:function(t,e,n){"use strict";var o=n(3);Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0,n(19),n(15),n(85),n(84),n(10);var r=o(n(48));n(7),n(6),n(716);var l=o(n(4)),c=n(86),d=o(n(869)),f=o(n(1226));function v(object,t){var e=Object.keys(object);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(object);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(object,t).enumerable}))),e.push.apply(e,n)}return e}var y={data:function(){return{components:[]}},created:function(){var t=this;if(window.AndroidContent){var content=window.AndroidContent.get();if(content){try{var e=JSON.parse(content);this.components=e}catch(t){console.error("Can`t convert content string to JSON")}this.generateCss(f.default.palette),(0,d.default)(f.default.palette.colors),this.generateComponentsCss(),setTimeout((function(){t.unlockDialogs()}),1e3)}else console.error("AndroidContent returned invalid content string")}else console.error("AndroidContent not found")},beforeCreate:function(){this.$store.dispatch("".concat(c.Helper.NAMESPACE,"/").concat(c.Helper.ACTIONS.RESET_DIALOGS))},methods:function(t){for(var i=1;i<arguments.length;i++){var source=null!=arguments[i]?arguments[i]:{};i%2?v(source,!0).forEach((function(e){(0,l.default)(t,e,source[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(source)):v(source).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(source,e))}))}return t}({},c.Helper.mapActions({resetDialogs:c.Helper.ACTIONS.RESET_DIALOGS,unlockDialogs:c.Helper.ACTIONS.UNLOCK_DIALOGS}),{generateComponentsCss:function(){this.css="",this.course&&f.default&&(this.css+=".g-button-style {\n                    border-radius: ".concat(f.default.buttonBorderRadius,"px !important;\n                }\n\n                .g-component-radius {\n                    border-radius: ").concat(f.default.componentBorderRadius,"px !important;\n                }\n\n                .g-component-shadow {\n                    box-shadow: 0 6px ").concat(f.default.componentShadowBlur,"px 0 rgba(83, 180, 231, 0.4) !important;\n                }\n                \n                .g-title-style {\n                    font-family: '").concat(f.default.titleFont,"' !important;\n                }\n\n                .g-text-style {\n                        font-family: '").concat(f.default.textFont,"' !important;\n                }"));var t=document.querySelector("style[components-styles]");if(t)t.innerHTML=this.css;else{var style=document.createElement("style");style.innerHTML=this.css,style.setAttribute("type","text/css"),style.setAttribute("components-styles",""),document.head.appendChild(style)}},generateCss:function(t){this.css="";for(var e=t.elementsColors,n=0,o=Object.entries(e);n<o.length;n++){var l=(0,r.default)(o[n],2),c=l[0],d=l[1],f=!0,v=!1,y=void 0;try{for(var m,h=d.type[Symbol.iterator]();!(f=(m=h.next()).done);f=!0){var E=m.value,O="";O="bg"===E?"background-color":"border"===E?"border-color":E;var element=c.split(".").join("-");this.css+="\n                        .p-".concat(element,"-").concat(E," {\n                            ").concat(O,": ").concat(d.value," !important;\n                        }")}}catch(t){v=!0,y=t}finally{try{f||null==h.return||h.return()}finally{if(v)throw y}}}var C=document.querySelector("style[element-styles]");if(C)C.innerHTML=this.css;else{var style=document.createElement("style");style.innerHTML=this.css,style.setAttribute("type","text/css"),style.setAttribute("element-styles",""),document.head.appendChild(style)}}})};e.default=y},1225:function(t,e,n){var content=n(1228);"string"==typeof content&&(content=[[t.i,content,""]]),content.locals&&(t.exports=content.locals);(0,n(2).default)("2fb1b4e6",content,!0,{sourceMap:!1})},1226:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;e.default={componentBorderRadius:20,componentShadowBlur:40,buttonBorderRadius:30,textFont:"Montserrat",titleFont:"Montserrat",palette:{colors:["#4C477E","#5E45DA","#906CE8","#E4C2E8","#D668E1"],elementsColors:{buttons:{type:["bg"],value:"#5E45DA"},"audio.track":{type:["bg"],value:"#5E45DA"},"dialog.outline":{type:["border"],value:"#D668E1"},"quote.common":{type:["bg","color","border"],value:"#906CE8"},"quote.icon":{type:["color"],value:"#D668E1"},"photo.mask":{type:["bg"],value:"#5E45DA"},"event.date":{type:["color"],value:"#E4C2E8"},"lessonfooter.stars":{type:["color"],value:"#5E45DA"},"lessonfooter.link":{type:["color"],value:"#D668E1"},"lessonfooter.name":{type:["color"],value:"#D668E1"},"quill.link":{type:["color"],value:"#5E45DA"},"quill.text":{type:["color"],value:"#D668E1"},"quill.fill":{type:["bg"],value:"#D668E1"},common:{type:["bg","color","border"],value:"#D668E1"}}}}},1227:function(t,e,n){"use strict";var o=n(1225),r=n.n(o);e.default=r.a},1228:function(t,e,n){(e=t.exports=n(1)(!1)).push([t.i,".content_3Y2xX{margin:0 auto;width:calc(100% - 40px)}",""]),e.locals={content:"content_3Y2xX"}},1229:function(t,e,n){"use strict";var o=function(){var t=this.$createElement,e=this._self._c||t;return e("div",{class:this.$style.content},[e("d-nodes-renderer",{attrs:{"full-width":"",dnodes:this.components}})],1)},r=[];n.d(e,"a",(function(){return o})),n.d(e,"b",(function(){return r}))}}]);