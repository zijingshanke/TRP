function initMenu(_parent)
{
    var as = document.getElementById(_parent).getElementsByTagName("a");
    var target = null;

    for (var i = 0; i < as.length; i++)
    {
        if (as[i].parentNode.className != "title")
        {
            as[i].className = "menuNormal";
            as[i].style.color = "#000000";

            as[i].onmouseover = function()
            {
                if (this.className.indexOf('menuClick') == -1)
                {
                    this.className = "menuNormal menuHover";
                    this.style.color = "#3B5998";
                }
            }

            as[i].onmouseout = function()
            {
                if (this.className.indexOf("menuClick") == -1)
                {
                    this.className = "menuNormal";
                    this.style.color = "#000000";
                }
            }

            as[i].onclick = function()
            {
                if (target != null)
                {
                    target.className = "menuNormal";
                    target.style.color = "#000000";
                }

                target = this;
                this.className = "menuNormal menuClick";
                this.style.color = "#FFFFFF";
            }

        }
    }
}