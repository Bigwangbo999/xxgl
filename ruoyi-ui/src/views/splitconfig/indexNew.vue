<template>

  <div class="detail-splitConfig-lvl" style="width:960px;height: 500px;padding: 50px">
    <el-form ref="splitConfigForm" :model="splitConfig" :rules="splitConfigRules" label-width="148px">
      <div style="padding-bottom: 20px">
        是否启用拆分:
        <el-radio-group size="small" v-model="splitConfig.isSplit" style="width: 400px;">
          <el-radio label='0'>不拆分</el-radio>
          <el-radio label='1'>拆分</el-radio>
        </el-radio-group>
      </div>
      <el-row>
        <el-col :span="12">
          <el-radio-group label-width="208px" v-model="splitConfig.isIncludingTax" :disabled="splitConfig.isSplit=='0'" style="margin-left: 55px;margin-top: 13.7px">
            <el-radio label='0'>拆分限额(不含税)</el-radio>
            <el-radio label='1'>拆分限额(含税)</el-radio>
          </el-radio-group>
        </el-col>
        <el-col :span="12">
          <el-form-item label-width="88px">
            <span>拆分行数</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="增值税专用发票" prop="specialQuotaValue">
            <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="0.1" :precision="2" :max="999999999" :controls="false" v-model="splitConfig.specialQuotaValue" placeholder="请输入限额" style="width:262px"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="specialQuotaRow" label-width="2px">
            <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="1" :max="10000"  :controls="false" v-model="splitConfig.specialQuotaRow" placeholder="请输入行数" style="width:262px;text-align:left"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="增值税普通发票" prop="commonQuotaValue">
            <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="0.1" :precision="2" :max="999999999" :controls="false" v-model="splitConfig.commonQuotaValue" placeholder="请输入限额" style="width:262px"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="commonQuotaRow" label-width="2px">
            <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="1" :max="10000" :controls="false" v-model="splitConfig.commonQuotaRow" placeholder="请输入行数" style="width:262px"/>
          </el-form-item>
        </el-col>
      </el-row>
      <!--<el-row>
        <el-col :span="12">
          <el-form-item label="增值税普通发票(卷式)" prop="rollQuotaValue">
            <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="0.1" :precision="2" :max="999999999" :controls="false" v-model="splitConfig.rollQuotaValue" placeholder="请输入限额" style="width:262px"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="rollQuotaRow" label-width="2px">
            <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="1" :max="8" :controls="false" v-model="splitConfig.rollQuotaRow" placeholder="请输入行数" style="width:262px"/>
          </el-form-item>
        </el-col>
      </el-row>-->

      <el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="增值税普通电子发票" prop="electronicQuotaValue">
              <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="0.1" :precision="2" :max="999999999" :controls="false" v-model="splitConfig.electronicQuotaValue" placeholder="请输入限额" style="width:262px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="electronicQuotaRow" label-width="2px">
              <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="1" :max="2000" :controls="false" v-model="splitConfig.electronicQuotaRow" placeholder="请输入行数" style="width:262px"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-row>

      <!--<el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="增值税电子专用发票" prop="electronicSpecialValue">
              <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="0.1" :precision="2" :max="999999999" :controls="false" v-model="splitConfig.electronicSpecialValue" placeholder="请输入限额" style="width:262px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="electronicSpecialRow" label-width="2px">
              <el-input-number size="small" :disabled="splitConfig.isSplit=='0'" :min="1" :max="2000" :controls="false" v-model="splitConfig.electronicSpecialRow" placeholder="请输入行数" style="width:262px"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-row>-->
    </el-form>

    <div slot="footer" class="btnBox">
      <el-button type="primary" size="mini"  :loading="saveOrUpdateSplitConfig" @click="submitSaveOrUpdate">保存</el-button>
    </div>


  </div>

</template>
<script>

  import { saveOrUpdate,getSplitConfig } from "@/api/vat_out/splitConfig";
  export default {
    name: "indexNew.vue",
    data(){
      return{
        loading : true,
        splitConfig:{

        },
        splitConfigRules:{

        },
        saveOrUpdateSplitConfig:false
      }
    },
    methods:{
      getSplitCOnfig(){
        this.loading = true
        getSplitConfig().then(res => {
          if(res.code == '0000'){
            this.splitConfig = res.data
            this.loading = false
          }
        })
      },
      submitSaveOrUpdate(){
        this.$refs["splitConfigForm"].validate(valid => {
          if (valid) {
            this.saveOrUpdateSplitConfig = true
            saveOrUpdate(this.splitConfig).then( res => {

              if(res.code == '0000'){
                this.msgSuccess(res.message)
              }else{
                this.msgError(res.message)
              }
              this.saveOrUpdateSplitConfig = false
            })
          }
        })

      }
    },
    created() {
      /*this.getSplitCOnfig();*/
    },
  }
</script>

<style scoped>
  .btnBox {
    width: 100%;
    display: flex;
    justify-content: center;
  }
</style>
<style lang="scss">
  .detail-splitConfig-lvl{
    .el-input__inner {
      text-align: left;
    }
  }
</style>
