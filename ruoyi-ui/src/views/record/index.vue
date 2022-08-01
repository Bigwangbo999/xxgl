<template>
  <div class="app-container">
    <div>
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="创建时间" prop="createTimeStr1">
          <el-date-picker
            clearable
            v-model="queryParams.createTimeStr1"
            type="daterange"
            value-format="yyyy-MM-dd"
            unlink-panels
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="购方名称" prop="payer">
          <el-input
            v-model="queryParams.payer"
            placeholder="请输入购方名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="发票号码" prop="fphm">
          <el-input
            v-model="queryParams.fphm"
            placeholder="请输入发票号码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="发票代码" prop="fpdm">
          <el-input
            v-model="queryParams.fpdm"
            placeholder="请输入发票代码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="开票状态" prop="kpzt">
          <el-select
            v-model="queryParams.kpzt"
            placeholder="开票状态"
            @change="stateChange"
            clearable
          >
            <el-option
              v-for="item in dataScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item
          label="开票时间"
          prop="startTimeArr"
          v-show="queryParams.kpzt == 2"
        >
          <el-date-picker
            v-model="queryParams.startTimeArr"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单编号" prop="documentNo">
          <el-input
            v-model="queryParams.documentNo"
            placeholder="请输入订单编号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="票种查询" prop="fplx">
          <el-select
            v-model="queryParams.fplx"
            placeholder="全部"
            @change="getList"
            clearable
          >
            <el-option
              v-for="item in ticketList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="作废状态"
          prop="invoiceStuatus"
          v-show="queryParams.kpzt == 2"
        >
          <el-select
            v-model="queryParams.redInvoiceStatus"
            placeholder="作废状态"
            @change="getList"
            clearable
          >
            <el-option
              v-for="item in voidScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-row>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
            <!-- <el-button type="primary" size="mini" @click="exportExcel"
              >导出当页</el-button
            > -->
            <el-dropdown @command="exportExcel" style="margin: 0 10px">
              <el-button type="primary" size="mini" class="el-dropdown-link">
                导出当页<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="1">含明细导出</el-dropdown-item>
                <el-dropdown-item command="2">不含明细导出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <!-- <el-button type="primary" size="mini" @click="exportExcelAll"
              >导出全部</el-button
            > -->
            <el-dropdown @command="exportExcelAll" style="margin: 0 10px">
              <el-button type="primary" size="mini" class="el-dropdown-link">
                导出全部<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="1">含明细导出</el-dropdown-item>
                <el-dropdown-item command="2">不含明细导出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-button type="primary" size="mini" @click="updateSelect"
              >刷新</el-button
            >

            <el-button type="primary" size="mini" @click="downLOadPDFToZIP"
              >下载发票PDF</el-button
            >
            <el-button type="primary" size="mini" @click="batchReopen"
              >重开</el-button
            >
            <el-button
              type="primary"
              size="mini"
              v-show="queryParams.kpzt == 2"
              @click="printSelect"
              >打印</el-button
            >
            <el-dialog
              :title="title"
              :visible.sync="centerDialogVisible"
              width="20%"
              center
            >
              <div v-if="title === '批量重开'">
                <span>已发起重开成功:{{ sucTotal }}条</span><br />
                <span style="color: red">已发起重开失败:{{ errTotal }}条</span>
              </div>
              <br />
              <span slot="footer" class="dialog-footer">
                <el-button
                  type="primary"
                  size="mini"
                  @click="errRedInvoice"
                  align="center"
                  >查看异常数据</el-button
                >
              </span>
            </el-dialog>
            <el-dialog title="错误信息" :visible.sync="dialogTableVisible">
              <el-table :data="gridData" style="width: 100%">
                <el-table-column
                  property="errDocumentNo"
                  label="订单编号"
                  width="330px"
                ></el-table-column>
                <el-table-column
                  property="errMsg"
                  label="错误信息"
                  width="350px"
                ></el-table-column>
              </el-table>
            </el-dialog>
          </el-form-item>
        </el-row>
      </el-form>
      <el-table
        :data="dataList"
        v-loading="loading"
        @selection-change="handleSelectionChange"
        :max-height="tableHeight"
        ref="tableList"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column
          prop="cust_code"
          label="税号/酒店编号"
          fixed
          :show-overflow-tooltip="true"
          width="170px"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="order_type"
          label="订单类型"
          fixed
          align="center"
          width="100"
        >
          <template slot-scope="scope">
            {{ getfplx(scope.row.order_type) }}
            <!--          <svg-icon :icon-class="scope.row.icon" />-->
          </template>
        </el-table-column>
        <el-table-column
          prop="saler"
          label="销售员"
          :show-overflow-tooltip="true"
          width="100"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="sale_type"
          label="销售类型"
          :show-overflow-tooltip="true"
          width="120"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="receipt_type"
          label="收款类型"
          :show-overflow-tooltip="true"
          width="120"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="contract"
          label="合同编号"
          :show-overflow-tooltip="true"
          width="160"
          align="center"
        ></el-table-column>
        <el-table-column prop="invoice_code" label="发票代码/发票号码" width="130">
          <template slot-scope="scope">
            {{ scope.row.invoice_code }}<br />
            {{ scope.row.invoice_value }}
          </template>
        </el-table-column>
        <el-table-column
          prop="invent_code"
          label="物料编码"
          :show-overflow-tooltip="true"
          width="160"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="rote"
          label="税率"
          :show-overflow-tooltip="true"
          width="100"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          :show-overflow-tooltip="true"
          width="160"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="status"
          label="开票状态"
          width="100"
          align="center"
        >
          <template slot-scope="scope">
            {{ getKpzt(scope.row.invoiceStatus) }}
          </template>
        </el-table-column>
        <el-table-column
          label="开票时间"
          align="center"
          prop="invoiceDate"
          width="100"
        >
        </el-table-column>
        <el-table-column
          prop="ghfNsrmc"
          label="购方名称"
          width="200"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="errReason"
          label="错误原因"
          width="150"
          align="center"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            <span :style="{ color: getColor(scope.row) }">
              {{ scope.row.errReason }}</span
            >
          </template>
        </el-table-column>
        <el-table-column
          prop="ghfEmail"
          label="购方电话/购方邮箱"
          width="200"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.ghfphone }}<br />
            {{ scope.row.ghfEmail }}
          </template>
        </el-table-column>
        <el-table-column
          prop="ghfTelAddr"
          label="购方地址"
          width="200"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="jshj"
          label="价税合计"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            <span :style="{ color: getFontColor(scope.row) }">
              {{ scope.row.jshj }}</span
            >
          </template>
        </el-table-column>
        <el-table-column
          prop="correspondOrderNo"
          width="170px"
          align="center"
          label="对应红票/蓝票"
        ></el-table-column>
                <el-table-column
          prop="ghfNsrmc"
          label="付款账号名称"
          width="200px"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="payerAccount"
          label="付款账号后六位"
          width="150px"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          label="操作"
          fixed="right"
          align="center"
          class-name="small-padding fixed-width"
          width="220px"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              :disabled="getPdf(scope.row.pdfResource)"
              @click="pdfVisible(scope.row.pdfResource)"
              >预览
            </el-button>
            <!-- 开票状态为完成才显示 -->
            <el-button
              v-if="
                scope.row.fplx == 51 &&
                (scope.row.invoiceStatus == 2 || scope.row.invoiceStatus == 3)
              "
              size="mini"
              type="text"
              icon="el-icon-folder-checked"
              @click="deliver(scope.row)"
              >交付
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-download"
              :disabled="getPdf(scope.row.pdfResource)"
              @click="downPDFT(scope.row)"
              >下载
            </el-button>

            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              v-if="
                scope.row.invoiceStatus == '22' ||
                scope.row.invoiceStatus == '' ||
                scope.row.invoiceStatus == null
              "
              @click="reopen(scope.row)"
              >重开
            </el-button>

                      </template>
        </el-table-column>
      </el-table>

      <el-dialog
        title="发票详情"
        :visible.sync="pdfDialogVisible"
        width="800px"
        height="600px"
        :before-close="handleClose"
      >
        <!--      <div class="scrollBox">-->
        <!--      <pdf  :src="pdfUrl"  ref="pdf"></pdf>-->
        <!--        </div>-->
        <iframe
          :src="pdfUrl"
          frameborder="0"
          style="width: 750px; height: 550px"
        ></iframe>
      </el-dialog>
      <el-dialog title="交付" :visible.sync="deliverDialogVisible" width="30%">
        <span
          >电子发票原件，将会以短信、邮件的方式发送到指定的手机号以及邮箱。</span
        >
        <el-form
          :model="rowData"
          :rules="rules"
          ref="rowData"
          style="margin-top: 20px"
        >
          <el-form-item label="交付手机" prop="phone">
            <el-input v-model="rowData.phone" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="交付邮箱" prop="mail">
            <el-input v-model="rowData.mail" style="width: 300px"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="deliverDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            :disabled="!rowData.phone && !rowData.mail"
            @click="handleDeliver('rowData')"
            >交 付</el-button
          >
        </span>
      </el-dialog>
      <pagination
        ref="pagRef"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
// import download from "downloadjs";
import axios from "axios";
import {
  selectInvoiceRecordByPage,
  reopen,
  updateSelect,
  downPDF,
  downLOadPDFToZIP,
  deliver,
} from "@/api/invoiceRecord/invoiceRecord.js";
import {
  redInvoice,
  reopenByBatch,
  printPDF,
} from "../../api/vat_out/document";

export default {
  data() {
    //电话校验
    let validatePhone = (rule, value, callback) => {
      if (value && !/^1[3456789]\d{9}$/.test(value)) {
        callback(new Error("手机号格式不正确"));
      } else return callback();
    };
    //邮箱校验
    let validateEmail = (rule, value, callback) => {
      if (
        value &&
        !/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(
          value
        )
      ) {
        callback(new Error("邮箱格式不正确"));
      } else return callback();
    };
    return {
      // 错误信息数据
      sucTotal: 0,
      errTotal: 0,
      gridData: [],
      dialogTableVisible: false,
      centerDialogVisible: false,
      title: "", //批量完成dialog的title
      deliverDialogVisible: false,
      rowData: { phone: "", mail: "" }, // 一行的数据存放
      redFlag: false,
      tableHeight: null,
      currentRow: [],
      // 数据范围选项
      dataScopeOptions: [
        {
          value: "2",
          label: "开票完成",
        },
        {
          value: "20",
          label: "开票中",
        },
        {
          value: "22",
          label: "开票失败",
        },
      ],
      voidScopeOptions: [
        {
          value: "3",
          label: "作废成功",
        },
        {
          value: "31",
          label: "作废中",
        },
        {
          value: "32",
          label: "作废失败",
        },
      ],
      invoiceTypeOptions: [
        {
          value: null,
          label: "全部",
        },
        {
          value: "1",
          label: "蓝票",
        },
        {
          value: "2",
          label: "红票",
        },
      ],
      ticketList: [
        {
          value: null,
          label: "全部",
        },
        {
          value: 51,
          label: "普通发票(电子)",
        },

        {
          value: 2,
          label: "普通发票(纸票)",
        },
        {
          value: 0,
          label: "专用发票",
        },
      ],
      total: 0,
      pdfDialogVisible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined,
        createTimeStr: undefined,
        fpdm: undefined,
        startTimeArr: [],
        endTimeStr: undefined,
        payer: undefined,
        kpzt: undefined,
        fphm: undefined,
        documentNo: undefined,
        redInvoiceStatus: undefined,
        invoiceType: undefined,
        createTimeStr1: [],
      },
      pdfUrl: "",
      dataList: [],
      loading: false,
      ids: [],
      //交付校验
      rules: {
        phone: [{ validator: validatePhone, trigger: "blur" }],
        mail: [{ validator: validateEmail, trigger: "blur" }],
      },
      //表格高度
      height: "600px",
    };
  },
  mounted() {
    //创建时间赋值为当月
    this.queryParams.createTimeStr1 = this.getstartTimeArr();
    this.getList();
    window.addEventListener("resize", this.handleScroll);
    this.handleScroll();
  },
  // //销毁,否则跳到别的路由还是会出现
  destroyed() {
    window.removeEventListener("resize", this.handleScroll);
  },
  methods: {
    //获取当前月开票时间范围
    getstartTimeArr() {
      let today = new Date();
      let tYear = today.getFullYear();
      let tMonth = today.getMonth() + 1;
      let tDate = today.getDate();
      tMonth = tMonth > 9 ? tMonth : "0" + tMonth;
      tDate = tDate > 9 ? tDate : "0" + tDate;
      return [`${tYear}-${tMonth}-01`, `${tYear}-${tMonth}-${tDate}`];
    },
    //开票记录打印时间格式化
    getExportExcelTime() {
      let today = new Date();
      let tYear = today.getFullYear();
      let tMonth = today.getMonth() + 1;
      let tDate = today.getDate();
      let tHours = today.getHours();
      let minutes = today.getMinutes();
      tMonth = tMonth > 9 ? tMonth : "0" + tMonth;
      tDate = tDate > 9 ? tDate : "0" + tDate;
      tHours = tHours > 9 ? tHours : "0" + tHours;
      minutes = minutes > 9 ? minutes : "0" + minutes;
      return "" + tYear + tMonth + tDate + tHours + minutes;
    },
    //监听height高度
    handleScroll() {
      this.tableHeight =
        window.innerHeight -
        this.$refs.queryForm.$el.scrollHeight -
        this.$refs.pagRef.$el.scrollHeight -
        140;
    },
    //开票状态改变
    stateChange() {
      if (this.queryParams.kpzt == "2") {
        this.queryParams.startTimeArr = this.getstartTimeArr();
      } else {
        this.queryParams.startTimeArr = [];
      }
      this.getList();
    },
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.kpzt == "2") {
        this.redFlag = true;
      } else {
        this.redFlag = false;
      }
      this.queryParams.endTimeStr = (
        this.queryParams.startTimeArr || []
      ).toString();
      this.queryParams.createTimeStr = (
        this.queryParams.createTimeStr1 || []
      ).toString();
      selectInvoiceRecordByPage(this.queryParams)
        .then((res) => {
          if (res.code == "200") {
            this.dataList = res.data.list;
            this.total = res.data.total;
          }
          this.loading = false;
        })
        .catch((err) => {
          this.loading = false;
        });
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.form = {
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "0",
        menuIds: [],
        deptIds: [],
        menuCheckStrictly: true,
        deptCheckStrictly: true,
        remark: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNumBer = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
      this.currentRow = selection;
    },
    exportExcel(command) {
      // console.log(this.queryParams);
      //当选择不是开票完成，则必须选择创建时间才能导出
      if (this.queryParams.kpzt != 2) {
        if (
          !this.queryParams.createTimeStr1 ||
          this.queryParams.createTimeStr1.length < 1
        ) {
          this.$message.warning("请选择创建时间");
          return;
        }
        this.queryParams.startTimeArr = null;
      } else {
        //当选择开票完成，则必须选择创建时间或者开票时间才能导出
        if (
          !this.queryParams.createTimeStr1 ||
          (this.queryParams.createTimeStr1.length < 1 &&
            (!this.queryParams.startTimeArr ||
              this.queryParams.startTimeArr.length < 1))
        ) {
          this.$message.warning("请选择创建时间或者开票时间");
          return;
        }
      }
      if (this.queryParams.createTimeStr1) {
        let flag = this.estimateRegistrationTime(
          this.queryParams.createTimeStr1[0],
          this.queryParams.createTimeStr1[1]
        );
        if (flag) {
          this.$message.warning("选择时间不能超过一年");
          return;
        }
      }
      if (this.queryParams.startTimeArr) {
        let flag = this.estimateRegistrationTime(
          this.queryParams.startTimeArr[0],
          this.queryParams.startTimeArr[1]
        );
        if (flag) {
          this.$message.warning("选择时间不能超过一年");
          return;
        }
      }
      this.queryParams.flag = command;
      this.download(
        "invoiceRecord/importExcel",
        {
          ...this.queryParams,
        },
        `开票记录${this.getExportExcelTime()}.xlsx`
      );
    },
    exportExcelAll(command) {
      //当选择不是开票完成，则必须选择创建时间才能导出
      if (this.queryParams.kpzt != 2) {
        if (
          !this.queryParams.createTimeStr1 ||
          this.queryParams.createTimeStr1.length < 1
        ) {
          this.$message.warning("请选择创建时间");
          return;
        }
        this.queryParams.startTimeArr = null;
      } else {
        //当选择开票完成，则必须选择创建时间或者开票时间才能导出
        if (
          !this.queryParams.createTimeStr1 ||
          (this.queryParams.createTimeStr1.length < 1 &&
            (!this.queryParams.startTimeArr ||
              this.queryParams.startTimeArr.length < 1))
        ) {
          this.$message.warning("请选择创建时间或者开票时间");
          return;
        }
      }
      if (this.queryParams.createTimeStr1) {
        let flag = this.estimateRegistrationTime(
          this.queryParams.createTimeStr1[0],
          this.queryParams.createTimeStr1[1]
        );
        if (flag) {
          this.$message.warning("选择时间不能超过一年");
          return;
        }
      }
      if (this.queryParams.startTimeArr) {
        let flag = this.estimateRegistrationTime(
          this.queryParams.startTimeArr[0],
          this.queryParams.startTimeArr[1]
        );
        if (flag) {
          this.$message.warning("选择时间不能超过一年");
          return;
        }
      }

      this.queryParams.flag = command;
      this.download(
        "invoiceRecord/allImport",
        { ...this.queryParams },
        `开票记录${this.getExportExcelTime()}.xlsx`
      );
    },
    getfplx(data) {
      if (data == "0") {
        return "专用发票";
      } else if (data == "2") {
        return "普通发票（纸票）";
      } else if (data == "51") {
        return "普通发票（电子）";
      }
    },

    //pdf预览显示
    getPdf(data) {
      return data == null || data == "" ? true : false;
    },
    getKpzt(data) {
      if (data == "2") {
        return "开票完成";
      } else if (data == "20") {
        return "开票中";
      } else if (data == "21") {
        return "开票中";
      } else if (data == "22") {
        return "开票失败";
      } else if (data == "24") {
        return "开票失败";
      } else if (data == "3") {
        return "发票已作废";
      } else if (data == "31") {
        return "发票作废中";
      }
    },
    getRedStatus(row) {
      return (
        row.fplx != 0 &&
        row.invoiceStatus == "2" &&
        row.invoiceType == "1" &&
        (row.correspondOrderNo == null ||
          row.correspondOrderNo == "" ||
          row.correspondOrderNo == row.documentNo)
      );
    },

    //查看异常数据
    errRedInvoice() {
      this.dialogTableVisible = true;
    },
    //预览PDF
    pdfVisible(row) {
      this.pdfDialogVisible = true;
      this.pdfUrl = row;
    },
    //交付
    deliver(row) {
      this.deliverDialogVisible = true;
      const { id, ghfphone, ghfEmail } = row;
      this.rowData = { id, phone: ghfphone, mail: ghfEmail };
    },
    //交付确认
    handleDeliver(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const { mail, phone, id } = this.rowData;
          deliver({ mail, phone, id }).then((res) => {
            if (res.code == "200") {
              this.$message.success("操作成功！");
              this.deliverDialogVisible = false;
              this.getList();
            } else {
              this.$message.info(res.msg);
            }
          });
        }
      });
    },
    //关闭弹窗
    handleClose() {
      this.pdfDialogVisible = false;
      this.pdfUrl = undefined;
    },
    fileLinkToStreamDownload(url) {
      window.location.href = url;
      this.$message.success("下载成功！");
    },
    //重开
    reopen(row) {
      reopen(row).then((res) => {
        if (res.code == "200") {
          this.$message.success("操作成功！");
          this.getList();
        } else {
          this.$message.info(res.msg);
        }
      });
    },
    //刷新状态
    updateSelect() {
      updateSelect().then((res) => {
        this.$message.success(res.msg);
        this.getList();
      });
    },
    //设置错误原因颜色
    getColor(row) {
      if (
        row.errReason != null ||
        row.errReason != undefined ||
        row.errReason != ""
      ) {
        if (
          row.invoiceStatus == "2" &&
          (row.correspondOrderNo == null ||
            row.correspondOrderNo != row.documentNo)
        ) {
          return "#61FF4D";
        } else if (row.invoiceStatus == "22") {
          return "#FF1F1F";
        } else if (
          row.invoiceStatus == "2" &&
          row.correspondOrderNo == row.documentNo
        ) {
          return "#FF1F1F";
        }
      }
    },
    getFontColor(row) {
      if (row.jshj < 0) {
        return "#FF1F1F";
      }
    },
    downPDFT(row) {
      downPDF(row.pdfResource)
        .then((res) => {
          const blob = new Blob([res], {
            type: "application/pdf;chartset=UTF-8",
          });
          const a = document.createElement("a");
          const URL = window.URL || window.webkitURL;
          const herf = URL.createObjectURL(blob);
          a.href = herf;
          a.download = row.fpdm + "_" + row.fphm + row.kpmc + ".pdf";
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
          window.URL.revokeObjectURL(herf);
        })
        .catch((err) => {
          // 创建blob对象，解析流数据
          const blob = new Blob([err], {
            // 如何后端没返回下载文件类型，则需要手动设置：type: 'application/pdf;chartset=UTF-8' 表示下载文档为pdf，如果是word则设置为msword，excel为excel
            type: "application/pdf;chartset=UTF-8",
          });
          const a = document.createElement("a");
          // 兼容webkix浏览器，处理webkit浏览器中href自动添加blob前缀，默认在浏览器打开而不是下载
          const URL = window.URL || window.webkitURL;
          // 根据解析后的blob对象创建URL 对象
          const herf = URL.createObjectURL(blob);
          // 下载链接
          a.href = herf;
          // 下载文件名,如果后端没有返回，可以自己写a.download = '文件.pdf'
          a.download = row.fpdm + "_" + row.fphm + row.kpmc + ".pdf";
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
          // 在内存中移除URL 对象
          window.URL.revokeObjectURL(herf);
        });
    },
    //批量下载文件
    downLOadPDFToZIP() {
      if (this.currentRow.length == 0) {
        this.$message.error("请选择一条开票成功的进行下载");
        return;
      }
      for (let i = 0; i < this.currentRow.length; i++) {
        if (
          this.currentRow[i].pdfResource == undefined ||
          this.currentRow[i].pdfResource == null ||
          this.currentRow[i].pdfResource == ""
        ) {
          return this.$message.warning("请选择开票成功的进行下载");
        }
      }
      if (this.currentRow.length == 1) {
        this.downPDFT(this.currentRow[0]);
      } else {
        let data = [];
        data = this.currentRow;
        // console.log(data)
        this.$confirm("是否确认下载pdf?", "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            return downLOadPDFToZIP(data);
          })
          .then((res) => {
            const blob = new Blob([res], {
              type: "application/zip;chartset=UTF-8",
            });
            const a = document.createElement("a");
            const URL = window.URL || window.webkitURL;
            const herf = URL.createObjectURL(blob);
            a.href = herf;
            a.download = "发票预览" + new Date().getTime() + ".zip";
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            window.URL.revokeObjectURL(herf);
          })
          .catch((err) => {
            // 创建blob对象，解析流数据
            const blob = new Blob([err], {
              // 如何后端没返回下载文件类型，则需要手动设置：type: 'application/pdf;chartset=UTF-8' 表示下载文档为pdf，如果是word则设置为msword，excel为excel
              type: "application/pdf;chartset=UTF-8",
            });
            const a = document.createElement("a");
            // 兼容webkix浏览器，处理webkit浏览器中href自动添加blob前缀，默认在浏览器打开而不是下载
            const URL = window.URL || window.webkitURL;
            // 根据解析后的blob对象创建URL 对象
            const herf = URL.createObjectURL(blob);
            // 下载链接
            a.href = herf;
            // 下载文件名,如果后端没有返回，可以自己写a.download = '文件.pdf'
            a.download = "发票预览" + new Date().getTime() + ".zip";
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            // 在内存中移除URL 对象
            window.URL.revokeObjectURL(herf);
          });
      }
    },
    //批量重开
    batchReopen() {
      if (this.currentRow.length == 0) {
        this.$message.error("请选择至少一条数据");
        return;
      }
      let data = [];
      this.currentRow.forEach((o) => data.push(o.id));
      this.$confirm("是否进行批量重开", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return reopenByBatch(data);
        })
        .then((res) => {
          if (res.code == 200) {
            this.$message.info("操作成功");
            this.centerDialogVisible = true;
            this.title = "批量重开";
            this.gridData = res.data.errResults;
            this.errTotal = res.data.errTotal;
            this.sucTotal = res.data.sucTotal;
            this.ids = [];
          } else {
            this.$message.info("操作失败");
          }
          this.getList();
        });
    },
    //批量打印
    printSelect() {
      let data = [];
      this.currentRow.forEach((o) => {
        if (o.fplx == 2) data.push(o);
      });
      if (data.length === 0) {
        this.$message.warning("请选择纸质发票");
        return;
      }
      printPDF(data).then((res) => {
        if (res.code == 200) {
          const a = document.createElement("a");
          a.href = `webprint:"2,${res.data.batchId}"`;
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
          this.$message.success("操作成功");
        } else {
          this.$message.error("操作失败");
        }
      });
    },
    estimateRegistrationTime(beginDate, endDate) {
      var begin = new Date(beginDate).getTime();
      var end = new Date(endDate).getTime();
      var day = (end - begin) / (24 * 60 * 60 * 1000);
      if (day > 365) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>
<style scoped>
.el-table .el-table__fixed {
  height: 100% !important;
  bottom: 16px !important;
}
</style>
