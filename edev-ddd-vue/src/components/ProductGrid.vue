<template>
  <div>
    <Panel style="height:100%">
      <template slot="header">
        <div class="f-row">
          <div class="f-full" style="line-height:30px">产品信息查询</div>
          <LinkButton iconCls="icon-add" @click="add()" style="margin-right: 10px;">添加</LinkButton>
          <SearchBox style="width:40%"
              placeholder="产品编号/产品名称"
              v-model="searchValue"
              @search="search($event)">
            <Addon>
              <span v-if="searchValue" class="textbox-icon icon-clear" title="清除" @click="searchValue=null"></span>
            </Addon>
          </SearchBox>
        </div>
      </template>
      <DataGrid :data="data" :multiSort="true" style="height: 100%;min-width: 400px;">
        <GridColumn field="id" title="产品编号" :sortable="true" width="100px"></GridColumn>
        <GridColumn field="name" title="产品名称" :sortable="true" width="300px">
          <template slot="body" slot-scope="scope">
            <u style="cursor: pointer" @click="edit(scope.row)">{{scope.row.name}}</u>
          </template>
        </GridColumn>
        <GridColumn field="price" title="价格" align="right" :sortable="true" width="100px">
          <template slot="body" slot-scope="scope">
            {{number_format(scope.row.price,2,".",",")}}
          </template>
        </GridColumn>
        <GridColumn field="originalPrice" title="原始价格" align="right" :sortable="true" width="100px">
          <template slot="body" slot-scope="scope">
            {{number_format(scope.row.originalPrice,2,".",",")}}
          </template>
        </GridColumn>
        <GridColumn field="unit" title="单位" align="center" width="50px"></GridColumn>
        <GridColumn field="classify" title="分类" width="100px">
        </GridColumn>
        <GridColumn field="supplier.name" title="供应商" width="200px">
          <template slot="body" slot-scope="scope">
            {{scope.row.supplier.name}}
          </template>
        </GridColumn>
        <GridColumn field="image" title="图片" width="200px"></GridColumn>
        <GridColumn field="tip" title="提示" align="center" width="50px"></GridColumn>
      </DataGrid>
      <template slot="footer">
        <Pagination :total="total" :pageSize="pageSize" :pageNumber="page" :layout="pageLayout"
          @pageChange="onPageChange($event)" displayMsg="第 {from} 页, 共 {total} 条记录"></Pagination>
      </template>
    </Panel>
    <ProductForm :product="product"></ProductForm>
  </div>
</template>

<script>
import ProductForm from '@/components/ProductForm'
export default {
  components: {
    ProductForm
  },
  mounted: function() {
    this.query()
    this.closeWindow()
  },
  data() {
    return {
      profix: '/api',
      total: null,
      data: [],
      page: 1,
      pageSize: 20,
      pagePosition: 'bottom',
      pageLayout: ['list', 'sep', 'first', 'prev', 'next', 'last', 'sep', 'refresh', 'info'],
      params: {},
      searchValue: null,
      product: {}
    }
  },
  methods: {
    number_format(number, decimals, decPoint, thousandsSep) {
      number = (number + '').replace(/[^0-9+-Ee.]/g, '')
      var n = !isFinite(+number) ? 0 : +number
      var prec = !isFinite(+decimals) ? 0 : Math.abs(decimals)
      var sep = (typeof thousandsSep === 'undefined') ? ',' : thousandsSep
      var dec = (typeof decPoint === 'undefined') ? '.' : decPoint
      var s = ''
      var toFixedFix = function (n, prec) {
        var k = Math.pow(10, prec)
        return '' + Math.ceil(n * k) / k
      }

      s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.')
      var re = /(-?\d+)(\d{3})/
      while (re.test(s[0])) {
        s[0] = s[0].replace(re, '$1' + sep + '$2')
      }

      if ((s[1] || '').length < prec) {
        s[1] = s[1] || ''
        s[1] += new Array(prec - s[1].length + 1).join('0')
      }
      return s.join(dec)
    },
    openWindow() {
      this.$children[1].openWindow()
    },
    closeWindow() {
      this.$children[1].closeWindow()
    },
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
      this.$data.params['value'] = event.value
      this.query()
    },
    onPageChange(event) {
      this.loadPage(event.pageNumber, event.pageSize)
    },
    loadPage(page, pageSize) {
      this.page = page
      this.pageSize = pageSize
      this.query()
    },
    add() {
      this.$data.product = {
        id: null,
        name: null,
        price: 0,
        originalPrice: 0,
        unit: '个',
        classify: null,
        supplierId: null,
        image: null,
        tip: null
      }
      this.openWindow()
    },
    edit(row) {
      this.$data.product = {
        id: row.id.toString(),
        name: row.name,
        price: row.price,
        originalPrice: row.originalPrice,
        unit: row.unit,
        classify: row.classify,
        supplierId: row.supplierId,
        image: row.image,
        tip: row.tip
      }
      this.openWindow()
    }
  }
}
</script>
