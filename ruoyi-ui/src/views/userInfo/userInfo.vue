<template>
  <div class="login-from-input">
    <div style="background-color: rgb(0, 115, 232);text-align: center; height:12vw">
      <img width=8% style="border-radius: 10%;margin-top: 2vw;margin-left: 36vw">
<!--           src="../../assets/images/cdttlogo.png">-->
      <div style="color: white;line-height: 12vw;float:right;margin-right: 35vw">成都天投</div>
    </div>
    <van-row>
      <van-col span="12">
        <van-cell :style="{backgroundColor: isCompanyColor,color:qyColor}" title="企业" style="text-align: center;" center :clickable="true"
                  @click="chooseEnterprise()"/>
      </van-col>
      <van-col span="12">
        <van-cell :style="{backgroundColor: isPersonedColor, color:grColor}" title="个人" style="text-align: center" center :clickable="true" @click="choosePersonal"/>
      </van-col>
    </van-row>
    <van-form validate-first class="van-form">
      <van-cell-group>
        <van-row v-if="isCompany">
          <van-col span="12">
            <van-field label="业务类型" :required="true" :readonly="true">
            </van-field>
          </van-col>
          <van-col span="12" v-if="isCompany">
            <div v-if="ywlx==='type1'">
              <van-cell is-link value="“总对总”批量担保" arrow-direction="down" @click="showActionBusinessSheet"/>
            </div>
            <div v-if="ywlx==='type2'">
              <van-cell is-link value="流动资金贷款" arrow-direction="down" @click="showActionBusinessSheet"/>
            </div>
            <div v-if="ywlx==='type3'">
              <van-cell is-link value="保函业务" arrow-direction="down" @click="showActionBusinessSheet"/>
            </div>
            <div v-if="ywlx==='type4'">
              <van-cell is-link value="委贷业务" arrow-direction="down" @click="showActionBusinessSheet"/>
            </div>
            <van-action-sheet
              v-model="showBusiness"
              :actions="businessType"
              :round="false"
              title="业务类型"
              @select="onSelectBusiness"
              cancel-text="取消"
            />
          </van-col>
          <van-col span="12" v-if="isCompany==false">
            <div>
              <van-cell value="普通发票（电子）" />
            </div>
          </van-col>
        </van-row>
        <van-row>

          <van-col span="12" style="height: 5vm">
            <van-field label="发票种类" :required="true" :readonly="true">
            </van-field>
          </van-col>
          <van-col span="12" v-if="isCompany"style="height: 5vm">
            <div v-if="fplx==='p'">
              <van-cell is-link value="普通发票（电子）" arrow-direction="down" @click="showActionSheet"/>
            </div>
            <div v-if="fplx==='c'">
              <van-cell is-link value="普通发票（纸质）" arrow-direction="down" @click="showActionSheet"/>
            </div>
            <div v-if="fplx==='s'">
              <van-cell is-link value="专用发票" arrow-direction="down" @click="showActionSheet"/>
            </div>
            <van-action-sheet
              v-model="show"
              :actions="actions"
              :round="false"
              title="发票种类"
              @select="onSelect"
              cancel-text="取消"
            ></van-action-sheet>
          </van-col>
          <van-col span="12" v-if="isCompany==false">
            <div>
              <van-cell title="普通发票（电子）" style="text-align: center" />
            </div>
          </van-col>
          <span v-if="fplx==='s'" style="color:rgb(255,176,93) ; display:block;font-size: 3vw;margin-left: 1.3vh;margin-top:-3vw;position: relative;top:-1.5vw;"><img style="width: 3vw;height: 3vm;display:inline-block;margin-bottom:-0.3vh;
          margin-right:0.5vh">
<!--           src="../../assets/images/yuan.png"-->
          />专用发票仅一般纳税人可选</span>
        </van-row>
        <van-row>
          <van-col span="8">
            <van-field label="付款账号名称" label-width="50vw" :required="true" :readonly="true">
            </van-field>
          </van-col>
          <van-col span="16">
            <van-field v-model="kpmcPayer" maxlength="100" input-align="right" clearable placeholder="请输入"
                       :rules="[
                      { required: true,trigger:'onBlur' }]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
                       @focus="inputContent"
            >

            </van-field>
            <div id="kpmc" v-show="kpmcShow">
              <ul>
                <li v-for="userinfo in filterUserInfo" class="kpmcli" @mousedown="selectUserInfo(userinfo)">{{userinfo.kpmc}}</li>
              </ul>
            </div>
          </van-col>
        </van-row>
        <van-row>
          <van-col span="8">
            <van-field label="付款账号后6位" label-width="50vw" :required="true" :readonly="true">
            </van-field>
          </van-col>
          <van-col span="16">
            <van-field v-model.trim="payerAccount"  maxlength="6" input-align="right" clearable placeholder="请输入"
                       :rules="[
                      { required: true,trigger:'onBlur' },
                      { pattern: /^([0-9]\d*|[0]{1,1})$/, message: '账号格式错误！'}]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
            >
            </van-field>
          </van-col>
        </van-row>

        <van-row>
          <van-col span="8">
            <van-field label="开票名称" label-width="50vw" :required="true" :readonly="true">
            </van-field>

          </van-col>
          <van-col span="16">
            <van-field v-model.trim="kpmc" maxlength="30" input-align="right" clearable placeholder="请输入"
                       :rules="[
                  { required: true,trigger:'onBlur' }]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
                       :readonly="true"
            >
            </van-field>

          </van-col>
        </van-row>
        <van-row>
          <van-col span="8">
            <van-field label="开票税号" label-width="50vw" :required="isCompany" :readonly="true">
            </van-field>
          </van-col>
          <van-col span="16">
            <van-field v-if="isCompany" v-model.trim="taxNo" maxlength="20" label-width="50vw" input-align="right" clearable placeholder="请输入"
                       :rules="[
                      { required: true,trigger:'onBlur' },
                      { pattern: /^[A-Za-z0-9]+$/, message: '税号格式错误！'}]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
            >
            </van-field>
            <van-field v-else v-model.trim="taxNo" maxlength="20" label-width="50vw" input-align="right" clearable placeholder="请输入"  @keydown="imeiKeyDow($event)"  @blur="responseBlur">
            </van-field>
          </van-col>
        </van-row>

        <van-row>
          <van-col span="8">
            <van-field label="邮箱" label-width="50vw" :required="true" :readonly="true">
            </van-field>
          </van-col>
          <van-col span="16">
            <van-field v-model.trim="email" input-align="right" clearable placeholder="请输入"
                       :rules="[
                      { required: true,trigger:'onBlur' },
                      { pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: '邮箱格式错误！'}]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
            >
            </van-field>
          </van-col>
        </van-row>
        <van-row>
          <van-col span="8">
            <van-field label="手机号" label-width="50vw" :required="true" :readonly="true" >
            </van-field>
          </van-col>
          <van-col span="16">
            <van-field v-model.trim="phone" input-align="right" clearable placeholder="请输入"
                   :rules="[
                      { required: true,trigger:'onBlur' },
                      { pattern: /^1[3456789]\d{9}$/, message: '手机号码格式错误！'}]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
            >
            </van-field>
          </van-col>
        </van-row>
<!--        <van-row>-->
<!--          <van-col span="5">-->
<!--            <van-field label="验证码" label-width="50vw" :required="true" :readonly="true" >-->
<!--            </van-field>-->
<!--          </van-col>-->
<!--          <van-col span="13">-->
<!--            <van-field v-model.trim="sms" input-align="left" maxlength="6" clearable placeholder="请输入"> </van-field>-->
<!--          </van-col>-->
<!--          <van-col span="6"  >-->
<!--            <van-count-down-->
<!--              style="padding: 3vw 5vw;text-align:right"-->
<!--              :time="1000 * 120"-->
<!--              format="mm:ss s"-->
<!--              v-if="isDataShow"-->
<!--              @finish="isDataShow = false"-->
<!--            />-->
<!--            <van-button-->
<!--              size="small"-->
<!--              native-type="button"-->
<!--              @click="sendMsg"-->
<!--              style="padding:6vw 3vw; color:#0073E8;border:none;font-size: 3.5vw"-->
<!--              v-else-->
<!--            >-->
<!--              获取验证码-->
<!--            </van-button>-->
<!--          </van-col>-->
<!--        </van-row>-->
        <van-row>
          <van-col span="8">
            <van-field v-if="fplx==='s'" label="地址电话" label-width="50vw" required clearable :readonly="true"></van-field>
            <van-field v-else label="地址电话" label-width="50vw" ></van-field>
          </van-col>
          <van-col span="16">
            <van-field v-if="fplx==='s'" v-model.trim="addressPhone" maxlength="100" input-align="right" clearable placeholder="请输入"
                       :rules="[
                      { required: true,trigger:'onBlur' }]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
            ></van-field>
            <van-field v-else v-model.trim="addressPhone" maxlength="100" input-align="right" clearable placeholder="请输入"  @blur="responseBlur"  @keydown="imeiKeyDow($event)">
            </van-field>
          </van-col>
        </van-row>
        <van-row>
          <van-col span="8">
            <van-field v-if="fplx==='s'" label="开户行及账号" label-width="50vw" required clearable :readonly="true"  @blur="responseBlur"  @keydown="imeiKeyDow($event)"></van-field>
            <van-field v-else label="开户行及账号" label-width="50vw" clearable :readonly="true"  @blur="responseBlur"  @keydown="imeiKeyDow($event)"></van-field>
          </van-col>
          <van-col span="16">
            <van-field v-if="fplx==='s'" v-model.trim="bankAccount" maxlength="100" input-align="right" clearable placeholder="请输入"
                       :rules="[
                { required: true,trigger:'onBlur' }]"
                       @keydown="imeiKeyDow($event)"
                       @blur="responseBlur"
            >

            </van-field>
            <van-field v-else v-model.trim="bankAccount" maxlength="100" input-align="right" clearable placeholder="请输入"   @blur="responseBlur"   @keydown="imeiKeyDow($event)" ></van-field>
          </van-col>
        </van-row>
        <van-row class="bzrow">
          <van-col span="4">
            <van-field label="备注" rows="2" label-width="50vw" :readonly="true">
            </van-field>
          </van-col>
          <van-col span="20" class="bzborder">
            <van-field v-model="remark" clearable placeholder="请输入" maxlength="230" type="textarea" rows="3">
            </van-field>
          </van-col>
        </van-row>
      </van-cell-group>
      <div class="button-div">
        <van-button class="submit" round type="info" color="rgb(0, 115, 232)" @click="submitUserInfo">申请开票</van-button>
      </div>
    </van-form>
    <van-dialog v-model="success"
                title="提交成功"
                confirmButtonText="确认"
                confirmButtonColor="#0073E8"
                class="success_dialog">
      <div class="success_text">您的开票申请已经受理，我公司将在30日内为您开具电子发票，请你耐心等待！</div>
    </van-dialog>
  </div>
</template>


<script>
import Vue from "vue";
import {saveInfo} from "../../api/system/userInfo/info";
import {sendMessage,selectAll} from "../../api/system/userInfo/info";
// import {
//   Button,
//   Cell,
//   Row,
//   Col,
//   Popup,
//   ActionSheet,
//   CellGroup,
//   Dialog,
//   Field,
//   List,
//   Notify,
//   Radio,
//   RadioGroup,
//   Form,
//   Icon,
//   CountDown
// } from 'vant';

export default {
  name: "userInfo",
  data() {
    return {
      kpmcShow:false,
      id: 0,
      success: false,
      isCompanyColor: '#FFFFFF',
      isPersonedColor: '#F7F7F7',
      qyColor:'#0073E8',
      grColor:'#A2A2A2',
      isCompany: true,
      isDataShow: false,
      timeData:{
        minutes:"",
        seconds:""
      },
      time:"120000",
      show: false,
      actions: [
        {
          name: '普通发票（电子）',
          color: '#0076FF',
          className: 'selectAction',
          value: 'p'
        },
      ],
      showBusiness:false,
      businessType: [
        {
          name: '“总对总”批量担保',
          color: '#0076FF',
          className: 'selectAction',
          value: 'type1'
        },
        {
          name: '流动资金贷款',
          color: '#0076FF',
          className: 'selectAction',
          value: 'type2'
        },
        {
          name: '保函业务',
          color: '#0076FF',
          className: 'selectAction',
          value: 'type3'
        },
        {
          name: '委贷业务',
          color: '#0076FF',
          className: 'selectAction',
          value: 'type4'
        }
      ],
      userInfos:[],
      ywlx:'type1',
      fplx: 'p',
      payer: null,
      payerAccount: null,
      kpmc: null,
      taxNo: null,
      email: null,
      phone: null,
      sms: "",
      addressPhone: null,
      bankAccount: null,
      remark: null,
      kpmcPayer:null
    }
  },
  components: {
    saveInfo,
    sendMessage,
    [Cell.name]: Cell,
    [Field.name]: Field,
    [CellGroup.name]: CellGroup,
    [Button.name]: Button,
    [Notify.name]: Notify,
    [RadioGroup.name]: RadioGroup,
    [Radio.name]: Radio,
    [List.name]: List,
    [Form.name]: Form,
    [Icon.name]: Icon,
    [Row.name]: Row,
    [Popup.name]: Popup,
    [Col.name]: Col,
    [ActionSheet.name]: ActionSheet,
    [Dialog.Component.name]: Dialog.Component,
    [CountDown.name]: CountDown
  },
  mounted() {
    this.id = this.$route.params.id;
  },
  created() {
    this.getList();
  },
  computed:{
    //通过开票名称输入来过滤数组
    filterUserInfo(){
      if(this.kpmcPayer){
        return   this.userInfos.filter(o=>{
          return o.kpmc.includes(this.kpmcPayer);
        })
      }else{
        return this.userInfos;
      }
    }
  },
  methods: {
    //点击选项框以外的地方
    clickoutside(){
      this.kpmcShow=false;
    },
    //获取用户信息列表
    getList(){
      selectAll().then(res=>{
        if(res.code==200){
          this.userInfos=res.data;
        }
      })
    },
    selectUserInfo(userInfo){
        this.kpmc=userInfo.kpmc;
        this.taxNo=userInfo.taxNo;
        this.phone=userInfo.phone;
        this.addressPhone=userInfo.addressPhone;
        this.kpmcShow=false;
        this.kpmcPayer=userInfo.kpmc;
        this.payer=userInfo.kpmc;
    },
    sendMsg(){
      if(this.phone == "" || !this.phone.match(/^1[3456789]\d{9}$/)){
        return;
      }
      sendMessage(this.phone).then(res => {
        if (res.code == 200) {
          this.changetime();
        }
      })
    },
    changetime(){
      this.isDataShow = !this.isDataShow;
    },
    showActionSheet() {
      this.show = true;
    },
    showActionBusinessSheet() {
      this.showBusiness = true;
    },
    onSelect(event) {
      this.fplx = event.value;
      this.show = false;
    },
    onSelectBusiness(event) {
      this.ywlx = event.value;
      this.showBusiness = false;
      if(event.value == 'type1'){
        this.actions =  [
          {
            name: '普通发票（电子）',
            color: '#0076FF',
            className: 'selectAction',
            value: 'p'
          },
        ]
        this.fplx = 'p'
      }else if(event.value == 'type2'){
        this.actions =  [
          {
            name: '专用发票',
            color: '#0076FF',
            className: 'selectAction',
            value: 's'
          }, {
            name: '普通发票（电子）',
            color: '#0076FF',
            className: 'selectAction',
            value: 'p'
          }
        ]
        this.fplx = 'p'
      }else if(event.value == 'type3'){
        this.actions =  [
          {
            name: '专用发票',
            color: '#0076FF',
            className: 'selectAction',
            value: 's'
          }, {
            name: '普通发票（电子）',
            color: '#0076FF',
            className: 'selectAction',
            value: 'p'
          }
        ]
        this.fplx = 'p'
      }
      else if(event.value == 'type4'){
        this.actions =  [
          {
            name: '普通发票（电子）',
            color: '#0076FF',
            className: 'selectAction',
            value: 'p'
          }
        ]
        this.fplx = 'p'
      }
    },
    //选择企业
    chooseEnterprise(){
      this.isCompany = true;
      this.isCompanyColor = '#FFFFFF';
      this.isPersonedColor='#F7F7F7';
      this.qyColor= '#0073E8';
      this.grColor= '#A2A2A2';
      this.ywlx='type1',
      this.kpmcPayer= null,
      this.payerAccount= null,
      this.kpmc= null,
      this.taxNo= null,
      this.email= null,
      this.phone=null,
      this.addressPhone= null,
      this.bankAccount= null,
      this.remark= null
    },
    //选择个人
    choosePersonal(){
      this.isCompany = false;
      this.fplx="p";
      this.isCompanyColor = '#F7F7F7';
      this.isPersonedColor='#FFFFFF';
      this.qyColor= '#A2A2A2';
      this.grColor= '#0073E8';
      this.ywlx=null,
      this.kpmcPayer= null,
      this.payerAccount=null,
      this.kpmc= null,
      this.taxNo= null,
      this.email= null,
      this.phone=null,
      this.addressPhone= null,
      this.bankAccount= null,
      this.remark= null
    },
    //键盘监听事件--禁止输入空格
    imeiKeyDow(e){
      if(e.keyCode == 32){
        e.returnValue = false
      }
    },
    //输入框失去焦点事件，去除空格
    responseBlur(data) {
      this.email = this.email.trim()
      this.kpmcShow=false;
      if(this.kpmcPayer!=null||this.kpmcPayer!=''){
        this.kpmc=this.kpmcPayer;
        this.payer=this.kpmcPayer;
      }else{
        this.kpmc='';
        this.taxNo='';
        this.phone='';
        this.addressPhone='';
      }
    },
    inputContent(){
      this.kpmc='';
      this.taxNo='';
      this.phone='';
      this.addressPhone='';
      this.kpmcShow=true
    },
    submitUserInfo() {
      var user = {
        fplx: this.fplx,
        payer: this.payer,
        payerAccount: this.payerAccount,
        kpmc: this.kpmc,
        taxNo: this.taxNo,
        email: this.email,
        phone: this.phone,
        addressPhone: this.addressPhone,
        bankAccount: this.bankAccount,
        remark: this.remark,
        source: this.id,
        sms: this.sms
      }
      if(user.kpmc!=user.payer){
        alert("付款名称与开票不一致");
        return;
      }
      saveInfo(user).then(res => {
        if (res.code == 200) {
          this.success = true;
          this.getList();
        }
      })
    }
  }
}
</script>

<style scoped>
.company {
  color: #0073E8;
  backgroundColor: #F7F7F7;
}

.van-form {
  margin: 0 3%;
}

.bzborder {
  border: 1px solid #F2F1F1;
  border-radius: 3%;
}

.bzrow {
  padding: 1vw 0vw 5vw 0vw;
}

.submit {
  text-align: center;
  width: 90vw;
  height: 10vw;
  margin-top: 5vw;
  margin-bottom: 5vw;
}

.button-div {
  text-align: center;
  margin-bottom: 5vw;
}

.van-row {
  border-bottom: 0.1vw solid #F2F1F1;
}

.selectAction {
  border-top: 0.1vw solid #F2F1F1;
}

.van-cell {
  font-size: 3.5vw;
  font-weight: 530;
}

.success_dialog {
  border-radius: 3%;
  width:85vw;
}

.success_text{
  padding: 5vw 6vw;
  font-size: 3.5vw;
}
#kpmc{
 overflow-y: scroll;
  white-space:nowrap;
  width: 60vw;
  border-radius: 1vw;
  height: 40vw;
  background-color: white;
  position: absolute;
  left:30vw;
  border: solid 1px silver;
  z-index: 9999;
  color: #323233;
  font-family: "宋体";
  font-size: 4vw;
}
.kpmcli{
  /*border-bottom: 1px solid silver;*/
  height: 8vw;
  line-height: 8vw;
}
</style>
