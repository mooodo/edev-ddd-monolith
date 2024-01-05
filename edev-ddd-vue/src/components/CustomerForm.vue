<template>
  <div>
    <Dialog ref="d1"
        :title="'用户档案详情'"
        :dialogStyle="{width:'390px',height:'380px'}"
        :modal="true"
        :draggable="true">
      <div style="margin-bottom: 10px;height: 85%;">
        <Tabs>
          <TabPanel :title="'基本信息'" style="height: 254px;">
            <Form :model="customer" class="submit-form">
              <div class="form-item">
                <Label for="id" align="top">用户编号：</Label>
                <TextBox inputId="id" v-model="customer.id"></TextBox>
              </div>
              <div class="form-item">
                <Label for="name" align="top">姓名：</Label>
                <TextBox inputId="name" v-model="customer.name" iconCls="icon-man"></TextBox>
              </div>
              <div class="form-item">
                <Label for="sex" align="top">性别：</Label>
                <ComboBox inputId="gender" :data="genders" v-model="customer.gender"
                 :editable="false" :panelStyle="{height:'auto'}">
                </ComboBox>
              </div>
              <div class="form-item">
                <Label for="birthdate" align="top">出生日期：</Label>
                <DateBox inputId="birthdate" v-model="customer.birthdate" format="yyyy-MM-dd"></DateBox>
              </div>
              <div class="form-item">
                <Label for="identification" align="top">身份证：</Label>
                <TextBox inputId="identification" v-model="customer.identification"></TextBox>
              </div>
              <div class="form-item">
                <Label for="phoneNumber" align="top">电话号码：</Label>
                <TextBox inputId="phoneNumber" v-model="customer.phoneNumber"></TextBox>
              </div>
            </Form>
          </TabPanel>
          <TabPanel :title="'用户地址'" style="height: 254px;">
            <DataGrid :data="customer.addresses" style="height: 100%;width: 700px;"
              :clickToEdit="true" selectionMode="cell" editMode="cell">
              <GridColumn field="country" title="国家" :editable="true">
              </GridColumn>
              <GridColumn field="province" title="省份" :editable="true">
              </GridColumn>
              <GridColumn field="city" title="地市" :editable="true">
              </GridColumn>
              <GridColumn field="zone" title="乡镇" :editable="true">
              </GridColumn>
              <GridColumn field="address" title="街道" :editable="true" width="300px"></GridColumn>
              <GridColumn field="phoneNumber" title="电话" :editable="true" width="100px"></GridColumn>
            </DataGrid>
          </TabPanel>
        </Tabs>
      </div>
      <div class="dialog-button" style="border-width: 0px;">
        <LinkButton style="width:80px" @click="save(customer)">保存</LinkButton>
        <LinkButton style="width:80px" @click="remove(customer)">删除</LinkButton>
        <LinkButton style="width:80px" @click="closeWindow()">关闭</LinkButton>
      </div>
    </Dialog>
  </div>
</template>

<script>
export default {
  props: {
    customer: {
      type: Object
    }
  },
  data() {
    return {
      profix: '/api',
      genders: [
        { value: '男', text: '男' },
        { value: '女', text: '女' }
      ]
    }
  },
  methods: {
    openWindow() {
      this.$refs.d1.open()
    },
    closeWindow() {
      this.$refs.d1.close()
    },
    save(customer) {
      this.$axios({
        url: this.$data.profix + '/orm/customer/save',
        method: 'post',
        data: customer
      }).then(rep => {
        this.$messager.alert({
          title: 'Save',
          icon: 'info',
          msg: '数据保存成功！'
        })
        this.closeWindow()
      }).catch(err => {
        this.$messager.alert({
          title: 'Error',
          icon: 'error',
          msg: '数据保存失败！'
        })
        console.info(err)
      })
    },
    remove(customer) {
      if (!customer.id || customer.id === null || customer.id === '') return
      this.$axios.get(this.$data.profix + '/customer/orm/customer/delete', {
        params: { customerId: customer.id }
      }).then(rep => {
        this.$messager.alert({
          title: 'Delete',
          icon: 'info',
          msg: '数据已经删除！'
        })
        this.closeWindow()
      }).catch(err => {
        this.$messager.alert({
          title: 'Error',
          icon: 'error',
          msg: '数据删除失败！'
        })
        console.info(err)
      })
    }
  }
}
</script>
<style>
  .submit-form {
    margin-top: 10px;
    margin-left: 10px;
    text-align: center;
  }
  .form-item {
    margin-bottom:10px;
    margin-right:10px;
    display:inline-block;
  }
</style>
