// ace editor setup
let aceEditor = ace.edit("editor");
aceEditor.setTheme("ace/theme/monokai");

let cookie = document.cookie;
if (cookie !== "") {
  let savedInput = decodeURIComponent(cookie);
  aceEditor.setValue(savedInput);
  aceEditor.clearSelection();
} else {
  aceEditor.setValue(".TL\nTitle\n.LP\nA Paragraph.");
  aceEditor.clearSelection();
}

// get macro set
let macrosSelector = document.getElementById("macros-selector");

// fetching pdf blobs and updating the view
let compileButton = document.getElementById("compile-button");
let embed = document.getElementById("viewer");

let compileFun = function() {
  let input = aceEditor.getValue();
  let encodedInput = encodeURIComponent(input);
  document.cookie = encodedInput;

  let selectedMacro = macrosSelector.value;

  let queryString = '/groffline?macro=' + selectedMacro + '&input=' + encodedInput;
  queryString = window.location.href + queryString;

  console.log("Sending query: " + queryString);
  
  let blob = 
  fetch(queryString)
  .then(response => response.blob())
  .then(blob => {
    let newBlob = new Blob([blob], {type: "application/pdf"})
    let url = URL.createObjectURL(newBlob);
    embed.src = url;
  });
};

compileButton.onclick = compileFun;
compileFun();
