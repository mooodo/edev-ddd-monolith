<template>
  <div>
    <Panel style="height:100%">
      <template slot="header">
        <div class="f-row">
          <div class="f-full" style="line-height:30px">订单信息跟踪</div>
        </div>
      </template>
      <DataGrid :data="data" :multiSort="true" style="height: 100%;min-width: 400px;">
        <GridColumn :expander="true" width="30"></GridColumn>
        <GridColumn field="id" title="订单编号" :sortable="true" width="100px"></GridColumn>
        <GridColumn field="customer.name" title="用户" :sortable="true" width="100px">
          <template slot="body" slot-scope="scope">
            <u style="cursor: pointer" @click="edit(scope.row)">{{scope.row.customer.name}}</u>
          </template>
        </GridColumn>
        <GridColumn field="amount" title="金额" align="right" :sortable="true" width="100px">
          <template slot="body" slot-scope="scope">
            {{number_format(scope.row.amount,2,".",",")}}
          </template>
        </GridColumn>
        <GridColumn field="orderTime" title="下单时间" align="center" :sortable="true" width="150px"></GridColumn>
        <GridColumn field="flag" title="状态" align="center" width="100px"></GridColumn>
        <GridColumn field="address.address" title="配送地址" width="300px">
          <template slot="body" slot-scope="scope">
            {{scope.row.address.address}}
          </template>
        </GridColumn>
        <template slot="detail" slot-scope="scope">
          <OrderDetail :orderItems="scope.row.orderItems"></OrderDetail>
        </template>
      </DataGrid>
      <template slot="footer">
        <Pagination :total="total" :pageSize="pageSize" :pageNumber="page" :layout="pageLayout"
          @pageChange="onPageChange($event)" displayMsg="第 {from} 页, 共 {total} 条记录"></Pagination>
      </template>
    </Panel>
    <CustomerForm :customer="customer"></CustomerForm>
  </div>
</template>

<script>
import OrderDetail from './OrderDetail.vue'
import CustomerForm from '@/components/CustomerForm'
export default {
  components: {
    OrderDetail, CustomerForm
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
      customer: {}
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
    query() {
      var pageObj = { page: this.$data.page - 1, size: this.$data.pageSize, count: this.$data.total }
      var params = Object.assign(this.$data.params, pageObj)
      this.$axios.post(this.$data.profix + '/query/orderQry', params).then(rep => {
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
      this.$data.params = event.value
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
    openWindow() {
      this.$children[1].openWindow()
    },
    closeWindow() {
      this.$children[1].closeWindow()
    },
    edit(row) {
      this.$data.customer = {
        id: row.customer.id.toString(),
        name: row.customer.name,
        gender: row.customer.gender,
        birthdate: new Date(row.customer.birthdate),
        identification: row.customer.identification,
        phoneNumber: row.customer.phoneNumber,
        addresses: row.customer.addresses
      }
      this.openWindow()
    }
  }
}
</script>
