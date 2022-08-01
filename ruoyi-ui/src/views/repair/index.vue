<template>
  <div class="app-container">
    <el-form
      :inline="true"
      :model="queryParams"
      ref="queryForm"
      class="demo-form-inline"
    >
      <el-form-item label="开票人">
        <el-input
          v-model="queryParams.clerk"
          clearable
          placeholder="请输入"
        ></el-input>
      </el-form-item>

      <el-button type="primary" @click="handleQuery">查询</el-button>
      <el-button type="primary" @click="handleInsert">添加</el-button>
      <el-button type="primary" @click="deleteInfo()">删除</el-button>
    </el-form>

    <el-table
      :data="dataList"
      ref="multipleTable"
      :max-height="tableHeight"
      @selection-change="checkRow"
      :min-width="true"
      :stripe="true"
      border
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column
        prop="sellerBankAccount"
        label="开户行账号"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="sellerNsrmc"
        label="销方名称"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="sellerNsrbh"
        label="销方税号"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="sellerAdd"
        label="销方地址"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="sellerTel"
        label="销方电话"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="payee"
        label="收款人"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="status"
        label="是否默认"
        :show-overflow-tooltip="true"
      >
        <!--<el-col :span="12">
             <el-switch
               v-model="sform.status"
               active-color="#13ce66"
               inactive-color="#C1C1C1"
               active-value=1
               inactive-value=0>
             </el-switch>
         </el-col>-->
        <template slot-scope="scope">
          {{ getfplx(scope.row.status) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="clerk"
        label="开票人"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="checker"
        label="复核人"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handle(scope.row)"
            >修改</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      ref="pagRef"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="handleQuery"
    />

    <!-- 修改部门对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="openSeller"
      width="750px"
      append-to-body
    >
      <el-form
        ref="sellerForm"
        :model="sellerForm"
        :rules="rules"
        label-width="110px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="销方名称" prop="sellerNsrmc">
              <el-input
                v-model="sellerForm.sellerNsrmc"
                disabled
                placeholder="请输入销方名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销方税号" prop="sellerNsrbh">
              <el-input
                v-model="sellerForm.sellerNsrbh"
                disabled
                placeholder="请输入销方税号"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行及账号" prop="sellerBankAccount">
              <el-input
                v-model="sellerForm.sellerBankAccount"
                placeholder="请输入开户行及账号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销方地址" prop="sellerAdd">
              <el-input
                v-model="sellerForm.sellerAdd"
                placeholder="请输入销方地址"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="销方电话" prop="sellerTel">
              <el-input
                v-model="sellerForm.sellerTel"
                placeholder="请输入销方电话"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收款人" prop="payee">
              <el-input v-model="sellerForm.payee" placeholder="请输入收款人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开票人" prop="clerk">
              <el-input v-model="sellerForm.clerk" placeholder="请输入开票人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="复核人" prop="checker">
              <el-input
                v-model="sellerForm.checker"
                placeholder="请输入复核人"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-col :span="12">
          <el-form-item label="默认" prop="status">
            <el-switch
              v-model="status"
              active-color="#13ce66"
              inactive-color="#C1C1C1"
              active-value="1"
              inactive-value="0"
            >
            </el-switch>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateFormSeller">确 定</el-button>
        <el-button @click="repairCancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="销方名称" prop="sellerNsrmc">
              <el-input
                v-model="form.sellerNsrmc"
                placeholder="请输入销方名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销方税号" prop="sellerNsrbh">
              <el-input
                v-model="form.sellerNsrbh"
                placeholder="请输入销方税号"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行及账号" prop="sellerBankAccount">
              <el-input
                v-model="form.sellerBankAccount"
                placeholder="请输入开户行及账号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销方地址" prop="sellerAdd">
              <el-input v-model="form.sellerAdd" placeholder="请输入销方地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="销方电话" prop="sellerTel">
              <el-input v-model="form.sellerTel" placeholder="请输入销方电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收款人" prop="payee">
              <el-input v-model="form.payee" placeholder="请输入收款人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开票人" prop="clerk">
              <el-input v-model="form.clerk" placeholder="请输入开票人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="复核人" prop="checker">
              <el-input v-model="form.checker" placeholder="请输入复核人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-col :span="12">
          <el-form-item label="默认" prop="status">
            <el-switch
              v-model="status"
              active-color="#13ce66"
              inactive-color="#C1C1C1"
              active-value="1"
              inactive-value="0"
            >
            </el-switch>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="sellerAdde">确 定</el-button>
        <el-button @click="repairCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  getList,
  getSeller,
  updateSeller,
  insertSeller,
  deleteInfo,
} from "../../views/../api/repair/repair.js";

export default {
  name: "index",
  data() {
    return {
      queryParams: {
        currentRow: [],
        pageNum: 1,
        pageSize: 10,
        clerk: "",
      },
      dataList: [],
      total: 0,
      showSearch: true,
      sellerForm: {
        id: "",
        deptId: "",
        sellerNsrmc: "",
        sellerNsrbh: "",
        sellerBankAccount: "",
        sellerAdd: "",
        sellerTel: "",
        payee: "",
        clerk: "",
        checker: "",
        status: "",
      },
      form: {
        id: "",
        deptId: "",
        sellerNsrmc: "",
        sellerNsrbh: "",
        sellerBankAccount: "",
        sellerAdd: "",
        sellerTel: "",
        payee: "",
        clerk: "",
        checker: "",
        status: "",
      },
      status: "",
      sform: {
        status: "",
      },
      currentRow: [],
      // 弹出层标题
      title: "",
      openSeller: false,
      open: false,
      tableHeight: null,
      // 表单校验
      rules: {
        sellerNsrmc: [
          { required: true, message: "销方名称不能为空", trigger: "blur" },
        ],
        sellerNsrbh: [
          { required: true, message: "销方税号不能为空", trigger: "blur" },
        ],
        sellerBankAccount: [
          { required: true, message: "银行账户不能为空", trigger: "blur" },
        ],

        sellerAdd: [
          { required: true, message: "销方地址不能为空", trigger: "blur" },
        ],
        payee: [{ required: true, message: "收款人不能为空", trigger: "blur" }],
        clerk: [{ required: true, message: "开票人不能为空", trigger: "blur" }],

        checker: [
          { required: true, message: "复核人不能为空", trigger: "blur" },
        ],
        sellerTel: [
          {
            required: true,
            /*pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,*/
            message: "请输入正确的电话号码",
            trigger: "blur",
          },
        ],
      },
    };
  },
  mounted() {
    this.handleQuery();
    window.addEventListener("resize", this.handleScroll);
    this.handleScroll();
  },
  methods: {
    handleQuery() {
      getList(this.queryParams).then((res) => {
        this.dataList = res.data.list;
        this.total = res.data.total;
      });
    },
    // 表格选中事件
    checkRow(currentRow) {
      this.currentRow = currentRow;
    },
    handleInsert() {
      this.reset();
      this.open = true;
      this.title = "新增销方信息";
    },
    // 表单重置
    reset() {
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
    /** 维护按钮操作 */
    handle(row) {
      this.reset();
      getSeller(row.id).then((res) => {
        this.sellerForm = res.data;
        this.openSeller = true;
        this.title = "修改销方信息";
        this.status = this.sellerForm.status;
      });
    },
    getfplx(data) {
      if (data == "1") {
        return "是";
      } else if (data == "0") {
        return "否";
      }
    },
    updateFormSeller() {
      this.sellerForm.status = this.status;
      updateSeller(this.sellerForm).then((res) => {
        if (res.code == "200") {
          this.openSeller = false;
          this.$modal.msgSuccess("成功");
          this.handleQuery();
        }
      });
    },
    // 维护取消按钮
    repairCancel() {
      this.openSeller = false;
      this.open = false;
      this.reset();
    },
    //新增方法
    sellerAdde() {
      this.form.status = this.status;
      // console.log(this.form)
      insertSeller(this.form).then((res) => {
        if (res.code == "200") {
          this.open = false;
          this.$modal.msgSuccess("成功");
          this.handleQuery();
          this.reset();
        }
      });
    },
    //删除方法
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
            this.handleQuery();
          }
        });
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
};
</script>

<style scoped>
</style>
