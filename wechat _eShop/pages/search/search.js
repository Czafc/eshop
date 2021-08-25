// pages/search/search.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    query : "",
    haveHistory : false,
    searchHistory:[]
  },


  onShow: function () {
    this.getHistory();
  },

  getHistory(){
    let searchHistory = wx.getStorageSync('search_history');
    if(searchHistory && searchHistory.length != 0) {
      this.setData({
        haveHistory : true,
        searchHistory
      })
    }
  },

  hanleInput(e){
    this.setData({
      query : e.detail.value
    })
  },

  handleSearch() {
    let query = this.data.query;
    if(!query || query === "") return;
    let searchHistory = [...this.data.searchHistory, query];
    wx.setStorageSync('search_history', searchHistory);
    wx.navigateTo({
      url: '/pages/goods_list/goods_list?query='+query
    })
  },
  
  handleDeleteHistory() {
    wx.removeStorageSync('search_history');
    this.setData({
      haveHistory : false,
      searchHistory : []
    })
  },

  handleTapHistory(e){
    let input = e.currentTarget.dataset.data;
    this.setData({
      query:input
    })
  }
})