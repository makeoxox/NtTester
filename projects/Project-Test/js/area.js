

 Nt_Import("Project-Test/js/md5.js")

function getArea(str) {
    var area = {}
    var index11 = 0
    var index1 = str.indexOf("省")
    if (index1 == -1) {
      index11 = str.indexOf("自治区")
      if (index11 != -1) {
        area.Province = str.substring(0, index11 + 3)
      } else {
        area.Province = str.substring(0, 0)
      }
    } else {
      area.Province = str.substring(0, index1 + 1)
    }
 
    var index2 = str.indexOf("市")
    if (index11 == -1) {
      area.City = str.substring(index11 + 1, index2 + 1)
    } else {
      if (index11 == 0) {
        area.City = str.substring(index1 + 1, index2 + 1)
      } else {
        area.City = str.substring(index11 + 3, index2 + 1)
      }
    }
 
    var index3 = str.lastIndexOf("区")
    if (index3 == -1) {
      index3 = str.indexOf("县")
      area.Country = str.substring(index2 + 1, index3 + 1)
    } else {
      area.Country = str.substring(index2 + 1, index3 + 1)
    }
    
     area.Address = str.substring(index3 +1, str.length())
    
    return area;
  }

