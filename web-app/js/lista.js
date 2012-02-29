/* 
 * The MIT License
 *
 * Copyright 2012 Universidad de Montemorelos A. C.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

highlightTableRows("lista");

function highlightTableRows(tableId) {
    var table = document.getElementById(tableId);
    var startRow = 0;
    var tbody = table.getElementsByTagName("tbody")[0];
    var rows = tbody.getElementsByTagName("tr");
    // add event handlers so rows light up and are clickable
    for (i=startRow; i < rows.length; i++) {
        rows[i].onmouseover = function() {
            this.style.cursor="pointer";
        };
        rows[i].onmouseout = function() {
        };
        rows[i].onclick = function() {
            var cell = this.getElementsByTagName("td")[0];
            var link = cell.getElementsByTagName("a")[0];
            if (link.onclick) {
                call = link.getAttribute("onclick");
                if (call.indexOf("return ") == 0) {
                    call = call.substring(7);
                }
                // this will not work for links with onclick handlers that return false
                eval(call);
            } else {
                location.href = link.getAttribute("href");
            }
            return false;
        }
    }
}
