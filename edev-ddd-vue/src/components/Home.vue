<template>
  <div>
    <div style="text-align: right;">
      <SearchBox style="width:50%;margin-right: 30px;" placeholder="商品名称"
          v-model="searchValue" @search="search($event)">
        <Addon>
          <span v-if="searchValue" class="textbox-icon icon-clear" title="清除" @click="searchValue=null"></span>
        </Addon>
      </SearchBox>
    </div>
    <div class="uni-product-list">
      <div class="uni-product" v-for="(product,index) in data" :key="index">
        <div class="image-view">
          <img class="uni-product-image" :src="product.image"/>
        </div>
        <div class="uni-product-title">{{product.name}}</div>
        <div class="uni-product-price">
          <div class="uni-product-price-favour">￥{{product.originalPrice}}</div>
          <div class="uni-product-price-original">￥{{product.price}}</div>
          <div class="uni-product-tip" v-if="product.tip">{{product.tip}}</div>
        </div>
      </div>
    </div>
    <div style="margin-top: 20px;margin-bottom: 10px;">
      <Pagination :total="total" :pageSize="pageSize" :pageNumber="page"
        @pageChange="onPageChange($event)"></Pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      profix: '/api',
      total: null,
      data: [],
      page: 1,
      pageSize: 8,
      params: {},
      searchValue: null
    }
  },
  mounted: function() {
    this.query()
  },
  methods: {
    query() {
      var pageObj = { page: this.$data.page - 1, size: this.$data.pageSize, count: this.$data.total }
      var params = Object.assign(this.$data.params, pageObj)
      this.$axios.post(this.$data.profix + '/query/productQry', params).then(rep => {
        this.$data.data = rep.data.data
        this.$data.total = (rep.data.count) ? rep.data.count : 1000
        this.$data.pageSize = (rep.data.size) ? rep.data.size : 20
      }).catch(err => {
        this.$messager.alert({
          title: 'Error',
          icon: 'error',
          msg: '数据查询失败！'
        })
        console.info(err)
      })
    },
    search(event) {
      this.$data.total = null
      this.$data.params['name'] = event.value
      this.query()
    },
    onPageChange(event) {
      this.loadPage(event.pageNumber, event.pageSize)
    },
    loadPage(page, pageSize) {
      this.page = page
      this.pageSize = pageSize
      this.query()
    }
  }
}
</script>

<style>

  .uni-product-list {
    display: flex;
    width: 100%;
    flex-wrap: wrap;
    flex-direction: row;
  }

  .uni-product {
    padding: 20px;
    display: flex;
    flex-direction: column;
  }

  .image-view {
    height: 330px;
    width: 330px;
    margin: 12px 0;
  }

  .uni-product-image {
    height: 330px;
    width: 330px;
  }

  .uni-product-title {
    width: 300px;
    word-break: break-all;
    display: -webkit-box;
    overflow: hidden;
    line-height: 1.5;
    text-overflow: ellipsis;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
  }

  .uni-product-price {
    margin-top: 10px;
    font-size: 20px;
    line-height: 1.5;
    position: relative;
  }

  .uni-product-price-original {
    color: #e80080;
  }

  .uni-product-price-favour {
    color: #888888;
    text-decoration: line-through;
    margin-left: 10px;
  }

  .uni-product-tip {
    position: absolute;
    right: 10px;
    background-color: #ff3333;
    color: #ffffff;
    padding: 0 10px;
    border-radius: 5px;
  }
</style>
