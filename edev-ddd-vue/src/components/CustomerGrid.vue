<template>
  <div>
    <Panel style="height:100%">
      <template slot="header">
        <div class="f-row">
          <div class="f-full" style="line-height:30px">用户档案查询</div>
          <LinkButton iconCls="icon-add" @click="add()" style="margin-right: 10px;">添加</LinkButton>
          <SearchBox style="width:40%"
              placeholder="用户编号/姓名"
              v-model="searchValue"
              @search="search($event)">
            <Addon>
              <span v-if="searchValue" class="textbox-icon icon-clear" title="清除" @click="searchValue=null"></span>
            </Addon>
          </SearchBox>
        </div>
      </template>
      <DataGrid :data="data" :multiSort="true" style="height: 100%;min-width: 400px;">
        <GridColumnGroup :frozen="true" align="left" width="200px">
          <GridHeaderRow>
            <GridColumn field="id" title="用户编号" :sortable="true"></GridColumn>
            <GridColumn field="name" title="姓名" :sortable="true">
              <template slot="body" slot-scope="scope">
                <u style="cursor: pointer" @click="edit(scope.row)">{{scope.row.name}}</u>
              </template>
            </GridColumn>
          </GridHeaderRow>
        </GridColumnGroup>
        <GridColumn field="gender" title="性别" align="center" width="50px"></GridColumn>
        <GridColumn field="birthdate" title="出生日期" align="center" width="150px"></GridColumn>
        <GridColumn field="identification" title="身份证" align="right" width="150px"></GridColumn>
        <GridColumn field="phoneNumber" title="电话号码" align="right" width="100px"></GridColumn>
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
import CustomerForm from '@/components/CustomerForm'
export default {
  components: {
    CustomerForm
  },
  mounted: function() {
    this.closeWindow()
    this.query()
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
    openWindow() {
      this.$children[1].openWindow()
    },
    closeWindow() {
      this.$children[1].closeWindow()
    },
    query() {
      var pageObj = { page: this.$data.page - 1, size: this.$data.pageSize, count: this.$data.total }
      var params = Object.assign(this.$data.params, pageObj)
      this.$axios.post(this.$data.profix + '/query/customerQry', params).then(rep => {
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
      this.$data.customer = {
        id: null,
        name: null,
        gender: null,
        birthdate: null,
        identification: null,
        phoneNumber: null
      }
      this.openWindow()
    },
    edit(row) {
      this.$data.customer = {
        id: row.id.toString(),
        name: row.name,
        gender: row.gender,
        birthdate: new Date(row.birthdate),
        identification: row.identification,
        phoneNumber: row.phoneNumber,
        addresses: row.addresses
      }
      this.openWindow()
    }
  }
}
</script>
