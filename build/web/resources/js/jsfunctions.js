/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function SelectAllCheckboxesSpecific(spanChk){
    var IsChecked = spanChk.checked;
    var Chk = spanChk;
    Parent = document.getElementById('form:mytable');
    var items = Parent.getElementsByTagName('input');
    for (i = 0; i < items.length; i++){
        if (items[i].id != Chk && items[i].type == "checkbox"){
            if (items[i].checked != IsChecked){
                items[i].click();
            }
        }
    }
}