//FormModificationDetector.js
//"borrowed" from  http://www.jroller.com/karthikg/entry/modelling_client_side_form_modifications

var formOnRender;
var detectFormModification = true;
var prevOnLoad; 

if(window.onload)prevOnLoad = window.onload;

function setDetectFormModification(flag){
  detectFormModification = flag;
}

window.onload=function(){ 
  if (prevOnLoad) prevOnLoad();
  var prevOnSubmit;
  if(document.forms[0].onsubmit)
	  prevOnSubmit=document.forms[0].onsubmit;
  
  document.forms[0].onsubmit= function() { 
      if(prevOnSubmit)prevOnSubmit();     
      setDetectFormModification(false); 
      return true; 
  };
  
  //retrieve the form values. 'wicketSerializeForm' 
  //function is defined in wicket-ajax.js. 
  formOnRender = wicketSerializeForm(${form_id});
}

var prevOnBeforeUnload;
if(window.onbeforeunload) prevOnBeforeUnload = window.onbeforeunload;

//Before the windown unloads, check for any modifications
window.onbeforeunload = function(event) {

  if(prevOnBeforeUnload) prevOnBeforeUnload();
  
  if (detectFormModification){
    formBeforeSubmit = wicketSerializeForm(${form_id});
    if (formOnRender != formBeforeSubmit) {
        return "${message}";
    }
  }
};