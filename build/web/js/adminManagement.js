function changePageSize(pageIndex, searchValue, category, min, max, status) {
    var pageSize = document.getElementById("cbPageSize").value;
    var form = document.getElementById("pageSizeForm");
    form.action = "/J3.L.P0019_Yellow_Moon_Shop/MainController?btnAction=ChangePage&txtPageIndex="+pageIndex+"&txtSearchValue="+searchValue+"cbCategory="+category+"&txtMin="+min+"txtMax="+max+"cbStatus="+status+"txtPageSize="+pageSize;
    form.submit();
}