// function([string1, string2],target id,[color1,color2])
/*
Dad the pressures and cares of everyday life are with you throughout the year. But this Christmas season I hope you experience the gifts of peace, love and happiness from those who love you.
*/

/*
Even though I am not there to give you the warmth of Christmas blessings, consider my love message as my love to you. Merry Christmas, my beautiful Tadie.

*/

/*
consoleText([
 'A gift',
 'made with love',
 'Merry Christmas',
 'Love',
 'Johnson'
 ], 'text',['white', 'tomato','green','red', 'blue']);

*/

 consoleText([
  'There is magic',
  'in the air',
  'at Christmas.',
  'May the sparkles',
  'and shimmering',
  'touch your spirit.',
  'Merry Christmas.',
  '~ Johnson'
  ], 'text',['white', 'purple', 'green','red', 'yellow', 'lime', 'green', 'blue']);


/*
 consoleText(['Hello World.',
  'Road of resistance',
  'Ender\'s Game.'], 'text',['white', 'tomato','rebeccapurple','red', 'brown', 'red', 'green', 'white', 'red', 'blue']);*/

function consoleText(words, id, colors) {
  if (colors === undefined) colors = ['#fff'];
  var visible = true;
  var con = document.getElementById('console');
  var letterCount = 1;
  var x = 1;
  var waiting = false;
  var target = document.getElementById(id)
  target.setAttribute('style', 'color:' + colors[0])
  window.setInterval(function() {

    if (letterCount === 0 && waiting === false) {
      waiting = true;
      target.innerHTML = words[0].substring(0, letterCount)
      window.setTimeout(function() {
        var usedColor = colors.shift();
        colors.push(usedColor);
        var usedWord = words.shift();
        words.push(usedWord);
        x = 1;
        target.setAttribute('style', 'color:' + colors[0])
        letterCount += x;
        waiting = false;
      }, 1000)
    } else if (letterCount === words[0].length + 1 && waiting === false) {
      waiting = true;
      window.setTimeout(function() {
        x = -1;
        letterCount += x;
        waiting = false;
      }, 1000)
    } else if (waiting === false) {
      target.innerHTML = words[0].substring(0, letterCount)
      letterCount += x;
    }
  }, 120)
  window.setInterval(function() {
    if (visible === true) {
      con.className = 'console-underscore hidden'
      visible = false;

    } else {
      con.className = 'console-underscore'

      visible = true;
    }
  }, 400)

  //alert("Good")
}


function continueToMain() {
    JSReceiver.continueToMain();
}

function skippy() {
    JSReceiver.skippy();
}



setTimeout(showCont, 40000);

function showCont() {
    $("#cont").show("slow");
}

