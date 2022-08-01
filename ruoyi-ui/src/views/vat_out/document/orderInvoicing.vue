<template>
  <div class="app-container">
    <div>
      <el-form :inline="true" ref="queryForm">
        <el-form-item label="发票类型" class="el-form-item-buttom">
          <template>
            <el-select
              v-model="queryParams.invoice_type"
              clearable
              placeholder="请输入发票类型"
            >
              <el-option
                v-for="item in invoice_types"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <span
          class="el-form-item-buttom"
          style="font-weight: 700; font-size: 14px; color: #606266"
          >导入时间</span
        >
        <el-date-picker
          value-format="yyyy-MM-dd"
          v-model="queryParams.sdate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          style="margin: 0 10px"
        >
        </el-date-picker>
        <el-form-item label="税号/酒店编号" class="el-form-item-buttom">
          <el-input
            size="small"
            v-model="queryParams.cust_cude"
            clearable
            placeholder="请输入税号/酒店编号"
            class="input-width"
          />
        </el-form-item>

        <el-form-item label="匹配状态" class="el-form-item-buttom">
          <template>
            <el-select
              v-model="queryParams.matchStatus"
              clearable
              placeholder="请选择"
            >
              <el-option
                v-for="item in status"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </template>
        </el-form-item>

        <br/>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >查询</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-upload"
          size="mini"
          @click="
            showExcelUploadFlag = true;
            fileList = [];
          "
          >导入</el-button
        >
        <el-button
          type="primary"
          size="mini"
          @click="
          inserInfo = true;
            fileList =[];"
        >新增</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-delete"
          size="mini"
          @click="deleteInfo()"
          >删除</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-refresh"
          size="mini"
          @click="reset()"
          >重置</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-sort"
          size="mini"
          :disabled="!(currentRow.length > 0)"
          @click="openInvoice"
          >匹配</el-button
        >

        <!--<el-button type="primary" icon="el-icon-search" size="mini" @click="showMatchFlag = true">预置匹配时间</el-button>-->
        <el-button
          type="primary"
          icon="el-icon-sort"
          size="mini"
          @click="kaipiao"
          >开票</el-button
        >
      </el-form>

      <div
        class="top-form"
        style="
          padding: 0px 10px 10px 0px;
          background-color: #ffffff;
          width: 100%;
          height: 100%;
        "
      ></div>
      <el-table
        ref="multipleTable"
        v-loading="loading"
        :data="tableList"
        :max-height="tableHeight"
        @selection-change="checkRow"
        :min-width="true"
        :stripe="true"
        border
      >


        <el-table-column
          type="selection"
          width="55"
          align="center"
        ></el-table-column>
        <el-table-column
          fixed
          prop="cust_code"
          width="115"
          label="税号或酒店编号"
          v-show="false"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          fixed
          prop="order_type"
          width="150"
          label="订单类型"
          :show-overflow-tooltip="true"
          align="center"
        >

        </el-table-column>

        <el-table-column
          prop="price_list"
          width="150"
          label="价目表"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="saler"
          width="100"
          label="销售员"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="sale_type"
          width="150"
          label="销售类型"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="receipt_type"
          width="150"
          label="收款类型"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="invoice_type"
          width="150"
          label="发票类型"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="contract"
          width="150"
          label="合同编号"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="invoice_code"
          width="150"
          label="发票代码"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="invoice_value"
          width="130"
          label="发票号码"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="invent_code"
          width="150"
          label="物料编码"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="rote"
          width="80"
          label="税率"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="price"
          width="100"
          label="价格"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="sdate"
          width="150"
          label="服务起始日"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="edate"
          width="150"
          label="服务终止日"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="catory_one"
          width="100"
          label="大类"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="catory_two"
          width="100"
          label="中类"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="catory_three"
          width="100"
          label="小类"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="notes"
          width="80"
          label="备注"
          :show-overflow-tooltip="true"
          align="center"
        ></el-table-column>
<!--        <el-table-column-->
<!--          prop=""-->
<!--          width="80"-->
<!--          label="客户分机号"-->
<!--          :show-overflow-tooltip="true"-->
<!--          align="center"-->
<!--        ></el-table-column>-->
        <el-table-column
          prop="status"
          width="80"
          label="状态"
          :show-overflow-tooltip="true"
          align="center"
        >
          <template slot-scope="scope">
            <span :style="{ color: getColor(scope.row.matchStatus) }">{{
              getKpzt(scope.row)
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="100"
          fixed="right"
        >
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              style="font-size: 12px"
              @click="selectDetail(scope.row.id)"
              >查看明细
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="page">
        <el-pagination
          ref="pagRef"
          @size-change="documentHandleSizeChange"
          @current-change="documentHandleCurrentChange"
          :current-page="queryParams.pageNum"
          :page-sizes="[10, 50, 100, 200]"
          :page-size="queryParams.pageSize"
          layout="total,sizes, prev, pager, next,total,jumper"
          :total="totalCount"
          style="margin: 10px; float: right"
        >
        </el-pagination>
      </div>
<!-- 新增发票信息对话框 -->
      <el-dialog
        :title="this.title"
        :visible.sync="invoiceInfo"
        width="750px"
        append-to-body
        @close="closeInsertDialog"
      >
        <el-form
          ref="insertSaleCifcForm"
          :model="saleCifcForm"
          :rules="rules"
          :inline="true"
          label-width="110px"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item label="税号/酒店编号" prop="cust_code">
                <el-input
                  v-model="saleCifcForm.cust_code"
                  placeholder="请输入税号/酒店编号"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="订单类型" prop="order_type">
                <el-input
                  v-model="saleCifcForm.order_type"
                  placeholder="请输入订单类型"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="价目表" prop="price_list">
                <el-input
                  v-model="saleCifcForm.price_list"
                  placeholder="请输入价目表"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="销售员" prop="saler">
                <el-input
                  v-model="saleCifcForm.saler"
                  placeholder="请输入销售员"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="销售类型" prop="sale_type">
                <el-input
                  v-model="saleCifcForm.sale_type"
                  placeholder="请输入销售类型"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="收款类型" prop="receipt_type">
                <el-input
                  v-model="saleCifcForm.receipt_type"
                  placeholder="请输入收款类型"
                  maxlength="30"
                  style="width: 200px"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="发票类型" prop="invoice_type">
                <el-input
                    v-model="saleCifcForm.invoice_type"
                    placeholder="请输入发票类型"
                    maxlength="30"
                    style="width: 200px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="合同编号" prop="contract">
                <el-input
                  v-model="saleCifcForm.contract"
                  placeholder="请输入合同编号"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="发票代码" prop="invoice_code">
                <el-input
                  v-model="saleCifcForm.invoice_code"
                  placeholder="请输入发票代码"
                  maxlength="30"
                  style="width: 200px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="发票号码" prop="invoice_value">
                <el-input
                  v-model="saleCifcForm.invoice_value"
                  placeholder="请输入发票号码"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="物料编码" prop="invent_code">
                <el-input
                  v-model="saleCifcForm.invent_code"
                  placeholder="请输入物料编码"
                  maxlength="30"
                  style="width: 200px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="税率" prop="rote">
                <el-input
                  v-model="saleCifcForm.rote"
                  placeholder="请输入税率"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="价格" prop="price">
                <el-input
                  v-model="saleCifcForm.price"
                  placeholder="请输入价格"
                  maxlength="30"
                  style="width: 200px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="大类" prop="catory_one">
                <el-input
                  v-model="saleCifcForm.catory_one"
                  placeholder="请输入大类"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="中类" prop="catory_two">
                <el-input
                  v-model="saleCifcForm.catory_two"
                  placeholder="请输入中类"
                  maxlength="30"
                  style="width: 200px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="小类" prop="catory_three">
                <el-input
                  v-model="saleCifcForm.catory_three"
                  placeholder="请输入小类"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="服务起始日" prop="sdate">
                <el-input
                  v-model="saleCifcForm.sdate"
                  placeholder="请输入服务起始日"
                  maxlength="30"
                  style="width: 200px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="服务终止日" prop="edate">
                <el-input
                  v-model="saleCifcForm.edate"
                  placeholder="请输入服务终止日"
                  maxlength="30"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="insertInfo">确 定</el-button>
          <el-button @click="closeInsertDialog">取 消</el-button>
        </div>
      </el-dialog>

      <el-dialog
        title="匹配"
        :visible.sync="showMatchFlag"
        :close-on-click-modal="false"
        top="8vh"
        class="customDia"
        width="450px"
        append-to-body
      >
        <div style="text-align: center" :data="matchList">
          <span style=""
            >共匹配{{
              this.matchList.success + this.matchList.fail
            }}条数据</span
          ><br /><br />
          <span>成功{{ this.matchList.success }}条</span><br /><br />
          <span>失败{{ this.matchList.fail }}条</span><br /><br />
        </div>
      </el-dialog>

      <el-dialog
        @close="showFlag = false"
        title="导入信息"
        :visible.sync="showExcelUploadFlag"
        :close-on-click-modal="false"
        top="8vh"
        class="customDia"
        width="450px"
        append-to-body
      >
        1、下载
        <a
          href="#"
          @click="currencyExcelDownLoad"
          style="text-decoration: none; color: #409eff"
          >《订单模板》</a
        ><br /><br />
        2、打开下载模板,将信息填入,上限1000条<br /><br />
        3、点击“选择文件”按钮,选择处理好的文件<br /><br />
        4、选择要导入的信息,点击“导入”<br /><br />
        <el-upload
          class="upload-demo"
          ref="upload"
          action=""
          accept=".xls,.xlsx"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false"
          :on-success="finishUploadSus"
          :on-error="finishUploaderr"
          :show-file-list="true"
          :on-change="onchangeFile"
          :multiple="false"
          :limit="1"
          :on-exceed="handleExceed"
          :http-request="httpRequest"
          size="small"
        >
          <el-button
            slot="trigger"
            style="background-color: rgb(255, 255, 255)"
            size="mini"
            >选择文件</el-button
          >
          <div slot="tip" class="el-upload__tip">已选择的文件：</div>

          <el-button
            :loading="uploadExcelLoading"
            :disabled="submitUploadFlag"
            style="
              margin-left: 10px;
              background-color: rgb(0, 143, 215);
              color: rgb(255, 255, 255);
            "
            size="mini"
            @click="submitUpload"
            >导入</el-button
          >
          <div v-if="showFlag">
            <span style="color: #ff1f1f">导入失败{{ errSize }}条</span><br />
            <span style="color: #61ff4d">导入成功{{ successSize }}条</span
            ><br />
            <el-button
              type="primary"
              size="mini"
              style="background-color: rgb(0, 143, 215)"
              @click="openPreview"
              >预览</el-button
            >
          </div>
        </el-upload>
      </el-dialog>
      <el-dialog
        title="发票详情"
        :visible.sync="detaildialang"
        @close="closeDbInfo"
      >
        <el-table
          ref="multipleTable"
          v-loading="loading"
          :data="detailIist"
          row-key="id"
          height="500px"
          @selection-change="checkRow"
          :min-width="true"
          :stripe="true"
          border
        >
          <el-table-column
            prop="ssflbm"
            width="150"
            label="税收分类编码"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="productName"
            width="120"
            label="商品(服务)"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="spect"
            width="150"
            label="规格型号"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="quantity"
            width="80"
            label="数量"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="price"
            width="100"
            label="单价(不含税)"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="incTaxPrice"
            width="100"
            label="单价(含税)"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="taxRate"
            width="80"
            label="税率"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="remark"
            width="80"
            label="备注"
            :show-overflow-tooltip="true"
            align="center"
          ></el-table-column>
        </el-table>
      </el-dialog>
      <el-dialog title="导入失败原因" :visible.sync="preview">
        <el-table :data="errResults">
          <el-table-column prop="errMsg" label="错误原因"> </el-table-column>
        </el-table>
      </el-dialog>
    </div>
  </div>
</template>
<!--
  订单明细
 -->
<script>
import {
  getDocumentLists,
  openInvoice,
  deleteInfo,
  openTicket,
  selectDetail, insertSaleCifc,
} from "@/api/vat_out/document";
import { importFile } from "@/api/import/import";
import ElRow from "element-ui/packages/row/src/row";
import {deleteGoods, insertGoods} from "../../../api/goods/goods";
import {updateSeller} from "@/api/repair/repair";

export default {
  components: { ElRow },
  name: "index",
  data() {
    return {
      queryParams: {
        // 单选记录
        currentRow: [],
        invoice_type: "",
        cust_cude: "",
        payer: "",
        sdate: [],
        pageNum: 1,
        pageSize: 10,
        payerAccount: "",
        invoiceStatus: null,
        matchStatus: null,
        createTimeStr: null,
        endTimeStr: null,
        ghfBankAccount: null,
        ghfTelAddr: null,
        spbmjc: null,

      },
      saleCifcForm: {
        id: null,
        cust_code: null,
        order_type: null,
        price_list: null,
        saler: null,
        sale_type: null,
        receipt_type: null,
        invoice_type: null,
        contract: null,
        invoice_code: null,
        invoice_value: null,
        invent_code: null,
        rote: null,
        price: null,
        sdate: null,
        edate: null,
        catory_one: null,
        catory_two: null,
        catory_three: null,
        notes: null,
        status: null,
      },
      rules: {

      },

      title: "",
      invoiceInfo: false,
      //明细列表
      detailIist: [],
      fileList: [],
      loading: true,
      tableList: [],
      matchList: {},
      tableHeight: null,
      totalCount: 0,
      //成功条数
      successSize: 0,
      //失败条数
      errSize: 0,
      showFlag: false,
      //预览弹窗
      preview: false,
      errResults: [],
      //发票明细弹窗
      detaildialang: false,
      //showExcelUploadFlag: false,
      deleteDocumentLoading: false,
      currentRow: [],
      openInvoiceLoading: false,
      currencyExcelUpload: "",
      showMatchFlag: false,
      uploadExcelLoading: false,
      submitUploadFlag: false,
      options: [
        {
          value: 2,
          label: "开票完成",
        },
        {
          value: 20,
          label: "票开中",
        },
        {
          value: 22,
          label: "票开失败",
        },
        {
          value: 21,
          label: "开票成功签章中",
        },
        {
          value: 3,
          label: "开票成功签章失败",
        },
      ],

      status: [
        {
          value: 1,
          label: "匹配成功",
        },
        {
          value: 2,
          label: "匹配失败",
        },
      ],

      invoice_types: [
        {
          value: 1,
          label: "电子发票",
        },
        {
          value: 2,
          label: "增值税专用发票",
        },
      ],
    };
  },
  methods: {
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getList();
    },
    // 表格选中事件
    checkRow(currentRow) {
      this.currentRow = currentRow;
    },
    //查询
    getList() {
      if (this.queryParams.sdate) {
        this.queryParams.createTimeStr = this.queryParams.sdate[0];
        this.queryParams.endTimeStr = this.queryParams.sdate[1];
      } else {
        this.queryParams.createTimeStr = null;
        this.queryParams.endTimeStr = null;
      }
      getDocumentLists(this.queryParams).then((res) => {
        if (res.code == "200") {
          this.loading = false;
          this.tableList = res.rows;
          this.totalCount = res.total;
        } else {
          this.msgError(res.message);
        }
      });
    },
    openPreview() {
      this.preview = true;
    },
    documentHandleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.getList();
    },
    documentHandleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    },

    // updateFormSeller() {
    //   this.sellerForm.status = this.status;
    //   insertSaleCifc(this.sellerForm).then((res) => {
    //     if (res.code == "200") {
    //       this.openSeller = false;
    //       this.$modal.msgSuccess("成功");
    //       this.handleQuery();
    //     }
    //   });
    // },

    inserInfo() {
      this.invoiceInfo = true;
      this.title = "新增商品";
    },
    //新增
    insertInfo() {
      if (this.isEmpty(this.saleCifcForm.cust_code)) {
        this.$message.warning("请输入税号/酒店编号");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.order_type)) {
        this.$message.warning("请输入订单类型");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.price_list)) {
        this.$message.warning("请输入价目表名称");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.saler)) {
        this.$message.warning("请输入销售员");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.sale_type)) {
        this.$message.warning("请输入销售类型");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.receipt_type)) {
        this.$message.warning("请输入收款类型");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.invoice_type)) {
        this.$message.warning("请输入发票类型");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.contract)) {
        this.$message.warning("请输入合同编号");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.invoice_code)) {
        this.$message.warning("请输入发票代码");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.invoice_value)) {
        this.$message.warning("请输入发票号码");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.invent_code)) {
        this.$message.warning("请输入物料编码");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.rote)) {
        this.$message.warning("请输入税率");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.price)) {
        this.$message.warning("请输入价格");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.sdate)) {
        this.$message.warning("请输入服务起始日");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.edate)) {
        this.$message.warning("请输入服务终止日");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.catory_one)) {
        this.$message.warning("请输入大类");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.catory_two)) {
        this.$message.warning("请输入中类");
        return;
      }
      if (this.isEmpty(this.saleCifcForm.catory_three)) {
        this.$message.warning("请输入小类");
        return;
      }
      //新增
      if (this.isEmpty(this.saleCifcForm.id)) {
        insertSaleCifc(this.saleCifcForm).then((res) => {
          if (res.code == "200") {
            this.invoiceInfo = false;
            this.$refs.insertSaleCifcForm.resetFields();
            this.getList();
          }
        });
      }
      // else {
      //   //修改
      //   updateSaleCifc(this.saleCifcForm).then((res) => {
      //     if (res.code == "200") {
      //       this.$message.success("修改成功");
      //       this.invoiceInfo = false;
      //       this.$refs.insertSaleCifcForm.resetFields();
      //       this.getList();
      //     }
      //   });
      // }
    },

    //关闭弹窗
    closeInsertDialog() {
      this.invoiceInfo = false;
      this.$refs.insertSaleCifcForm.resetFields();
      this.refSaleCifcEntity();
    },
    refSaleCifcEntity(){
        this.saleCifcForm.id = null;
        this.saleCifcForm.cust_code= null;
        this.saleCifcForm.order_type= null;
        this.saleCifcForm.price_list= null;
        this.saleCifcForm.saler= null;
        this.saleCifcForm.sale_type= null;
        this.saleCifcForm.receipt_type=null;
        this.saleCifcForm.invoice_type= null;
        this.saleCifcFormcontract= null;
        this.saleCifcForm.invoice_code= null;
        this.saleCifcForm.invoice_value= null;
        this.saleCifcForm.invent_code= null;
        this.saleCifcForm.rote= null;
        this.saleCifcForm.price= null;
        this.saleCifcForm.sdate= null;
        this.saleCifcForm.edate= null;
        this.saleCifcForm.catory_one= null;
        this.saleCifcForm.catory_two= null;
        this.saleCifcForm.catory_three= null;
        this.saleCifcForm.notes= null;
        this.saleCifcForm.status= null;
    },
    reset1() {
      this.form = {
        id: undefined,
        deptId: undefined,
        sellerNsrmc: undefined,
        sellerNsrbh: undefined,
        sellerBankAccount: undefined,
        sellerAdd: undefined,
        sellerTel: undefined,
        payee: undefined,
        clerk: undefined,
        checker: undefined,
        status: undefined,
      };
      this.resetForm("form");
    },
    //删除
    deleteDocument() {
      let ids = [];
      this.currentRow.forEach((item, index) => {
        ids.push(item.id);
      });
      this.$confirm("是否确认删除" + ids.length + "条订单？", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.deleteDocumentLoading = true;
        deleteDocumentList(ids).then((res) => {
          if (res.code == "200") {
            this.$modal.msgSuccess("删除成功");
            this.getList();
          } else {
            this.msgError(res.message);
          }
          this.deleteDocumentLoading = false;
        });
      });
    },
    //开票
    kaipiao() {
      let flagMatch = false;
      let flagK = false;
      // console.log(this.currentRow);
      if (this.currentRow.length == 0) {
        this.$message.warning("请选择一条信息进行开票");
        return;
      }
      this.currentRow.forEach((o) => {
        if (o.matchStatus != "1") {
          flagMatch = true;
        } else if (o.invoiceStatus != null) {
          flagK = true;
        }
      });

      if (flagMatch) {
        this.$message.warning("请选择匹配成功的进行开票");
        return;
      }
      if (flagK) {
        this.$message.warning("请选择未开具的发票的订单进行开票");
        return;
      }
      this.loading = true;
      openTicket(this.currentRow).then((res) => {
        this.loading = false;
        if (res.code == "200") {
          this.$message.success("开票成功");
        } else {
          this.$message.error(res.msg);
        }
        this.getList();
      });
    },
    //提交匹配
    openInvoice() {
      let ids = [];
      let flag = false;
      this.currentRow.forEach((item, index) => {
        if (item.matchStatus == "1") {
          flag = true;
        }
        ids.push(item.id);
      });
      if (flag) {
        this.$message.warning("选择的银行流水号已经匹配成功，请勿重新匹配");
        return;
      }
      this.$confirm("共勾选" + ids.length + "条订单，确认是否匹配", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.openInvoiceLoading = true;
        openInvoice(ids).then((res) => {
          if (res.code == "200") {
            (this.showMatchFlag = true), (this.matchList = res.data);
            this.getList();
          } else {
            this.msgError(res.msg);
          }
          this.openInvoiceLoading = false;
        });
      });
    },

    //通用发票excel文件下载
    currencyExcelDownLoad() {
      //通用Excel导入模板下载
      this.$request
        .get("/invoice/downloadExcel", { responseType: "blob" })
        .then((res) => {
          if (res) {
            const content = res;
            const blob = new Blob([content]);
            const fileName = `document1.xlsx`;
            if ("download" in document.createElement("a")) {
              // 非IE下载
              const elink = document.createElement("a");
              elink.download = fileName;
              elink.style.display = "none";
              elink.href = URL.createObjectURL(blob);
              document.body.appendChild(elink);
              elink.click();
              URL.revokeObjectURL(elink.href); // 释放URL 对象
              document.body.removeChild(elink);
            } else {
              // IE10+下载
              navigator.msSaveBlob(blob, fileName);
            }
          }
        });
    },

    onchangeFile(file) {
      if (!file.response) {
        this.submitUploadFlag = false;
      }
    },
    onchangeCustomizeFile(file) {
      if (!file.response) {
        this.submitCustomizeUploadFlag = false;
      }
    },
    handlePreview(file) {},
    handleCustomizePreview(file) {},
    handleRemove(file, fileList) {
      this.submitUploadFlag = true;
    },
    handleCustomizeRemove(file, fileList) {
      this.submitCustomizeUploadFlag = true;
    },
    finishCustomizeUploadSus(response, file) {
      if (response.code == "200") {
        this.$alert("上传成功", "成功提示", {
          confirmButtonText: "确定",
        });
        this.customizeFileList = [];
        this.handleQuery();
        this.submitCustomizeUploadFlag = true;
      } else {
        this.$alert(response.message, "错误提示", {
          confirmButtonText: "确定",
        });
        this.customizeFileList = [];
        this.submitCustomizeUploadFlag = true;
      }
      this.uploadCustomizeExcelLoading = false;
    },
    finishUploadSus(response, file) {
      if (response.code == "200") {
        this.$alert("上传成功", "成功提示", {
          confirmButtonText: "确定",
        });
        this.fileList = [];
        this.handleQuery();
        this.submitUploadFlag = true;
      } else {
        this.$alert(response.message, "错误提示", {
          confirmButtonText: "确定",
        });
        this.fileList = [];
        this.submitUploadFlag = true;
      }
      this.uploadExcelLoading = false;
    },
    finishUploaderr(err, file, fileList) {
      //debugger
      this.$alert("上传失败", "错误提示", {
        confirmButtonText: "确定",
      });
      this.fileList = [];
      this.submitUploadFlag = true;
      this.uploadExcelLoading = false;
    },
    finishCustomizeUploaderr(err, file, fileList) {
      this.$alert("上传失败", "错误提示", {
        confirmButtonText: "确定",
      });
      this.customizeFileList = [];
      this.submitCustomizeUploadFlag = true;
      this.uploadCustomizeExcelLoading = false;
    },
    handleExceed(files, fileList) {
      this.$message.warning(`每次只能上传一个文件！`);
    },
    handleCustomizeExceed(files, fileList) {
      this.$message.warning(`每次只能上传一个文件！`);
    },
    // 文件上传
    submitUpload() {
      /*console.log(this.fileList)
        importFile(this.fileList).then(res => {

        })*/
      this.$refs.upload.submit();
    },
    httpRequest(param) {
      let fileObj = param.file; // 相当于input里取得的files
      let fd = new FormData(); // FormData 对象
      fd.append("file", fileObj); // 文件对象
      /*fd.append('platNum', this.fileList.platNum)
        fd.append('taskName', this.fileList.taskName)*/

      /*let url = process.env.CMS1_BASE_API + '/uninvoice/importExcel'*/
      let url = "/uninvoice/importExcel";
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      this.loading = true;
      importFile(url, fd).then((res) => {
        if (res.code == "200") {
          this.$message.success("导入成功！");
          this.showFlag = true;
          this.errSize = res.data.errTotal;
          this.successSize = res.data.sucTotal;
          this.errResults = res.data.errResults;
          this.loading = false;
        } else if (res.data.code != "200") {
          this.showExcelUploadFlag = false;
          this.loading = false;
          // console.log(res.data.msg)
          this.$message.error(res.data.msg);
        }
        this.getList();
      });
      /*axios.post(url, fd, config).then(res=>{
          if(res.code===200){
            this.submitForm();//提交表单
          }
        })*/
    },
    selectDetail(id) {
      selectDetail(id).then((res) => {
        if (res.code == 200) {
          this.detailIist = res.data;
          this.detaildialang = true;
        }
      });
    },
    closeDbInfo() {
      this.detaildialang = false;
      this.detailIist = [];
    },
    getKpzt(data) {
      if (data.invoiceStatus != null) {
        return "开票成功";
      }
      if (data.status == 0) {
        return "待匹配";
      } else if (data.status == 1) {
        return "匹配成功";
      } else if (data.status == 2) {
        return "匹配失败";
      }
    },
    getColor(data) {
      if (data == 1) {
        return "#118801FF";
      } else if (data == 2) {
        return "#FF1F1F";
      }
    },
    deleteInfo() {
      let ids = [];
      this.currentRow.forEach((item, index) => {
        ids.push(item.id);
      });
      if (ids.length == "0") {
        return this.$message.info("请选择一条数据！");
      }
      this.$confirm("是否删除所选发票信息?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return deleteInfo(ids);
        })
        .then((res) => {
          if (res.code == "200") {
            this.$message.success("操作成功！");
            this.getList();
          }
        });
    },
    //重置输入框
    // 表单重置
    reset() {
      this.queryParams = {
        invoice_type: null,
        cust_cude: null,
        payer: null,
        payerAccount: null,
        invoiceStatus: null,
        matchStatus: null,
      };

      this.resetForm("queryParams");
      this.getList();
    },
    //监听height高度
    handleScroll() {
      this.tableHeight =
        window.innerHeight -
        this.$refs.queryForm.$el.scrollHeight -
        this.$refs.pagRef.$el.scrollHeight -
        130;
    },
  },

  mounted() {
    this.getList();
    window.addEventListener("resize", this.handleScroll);
    this.handleScroll();
  },
  // //销毁,否则跳到别的路由还是会出现
  destroyed() {
    window.removeEventListener("resize", this.handleScroll);
  },
};
</script>


<style>
.input-width {
  width: 230px;
}
.top-form {
  display: flex;
  justify-content: space-between;
}
.el-form-item-buttom {
}
.el-table .el-table__fixed {
  height: auto !important;
  bottom: 16px !important;
}
</style>

