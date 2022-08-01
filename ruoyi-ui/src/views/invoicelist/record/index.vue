<template>
<!--  <div class="app-container">-->
  <div class="app-container3">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发票号码" prop="postCode">
        <el-input
          v-model="queryParams.postCode"
          placeholder="请输入发票号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="类型" prop="status">
        <el-select v-model="queryParams.status" placeholder="发票类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:post:export']"
        >导出PDF</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="xh" />
      <el-table-column label="税号" align="center" prop="sh" />
      <el-table-column label="订单类型" align="center" prop="ddlx" />
      <el-table-column label="销售员" align="center" prop="xsy" />
      <el-table-column label="销售类型" align="center" prop="xslx" />
      <el-table-column label="收款类型" align="center" prop="sklx" />
      <el-table-column label="发票类型" align="center" prop="fplx" />
      <el-table-column label="合同编号" align="center" prop="htbm" />
      <el-table-column label="发票代码" align="center" prop="fpdm" />
      <el-table-column label="发票号码" align="center" prop="fphm" />
      <el-table-column label="物料编码" align="center" prop="wlbm" />
      <el-table-column label="税率" align="center" prop="sl" />
      <el-table-column label="盘号" align="center" prop="ph" />
      <el-table-column label="数量" align="center" prop="count" />
      <el-table-column label="价格" align="center" prop="jg" />
      <el-table-column label="服务起始日" align="center" prop="fwqsr" />
      <el-table-column label="服务结束日" align="center" prop="fwzzr" />
      <el-table-column label="大类" align="center" prop="dl" />
      <el-table-column label="中类" align="center" prop="zl" />
      <el-table-column label="小类" align="center" prop="xl" />
      <el-table-column label="备注" align="center" prop="bz" />
      <el-table-column label="单位" align="center" prop="dw" />
      <el-table-column label="客户分机号" align="center" prop="khfjh" />
      <el-table-column label="行号" align="center" prop="hh">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:post:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:post:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="序号" prop="xh">
          <el-input v-model="form.xh" placeholder="请输入序号" />
        </el-form-item>
        <el-form-item label="税号" prop="sh">
          <el-input v-model="form.sh" placeholder="请输入税号" />
        </el-form-item>
        <el-form-item label="订单类型" prop="order_type">
          <el-input v-model="form.order_type" placeholder="请输入订单类型" />
        </el-form-item>
        <el-form-item label="价目表" prop="price_list">
          <el-input v-model="form.price_list" placeholder="请输入价目表" />
        </el-form-item>
        <el-form-item label="销售员" prop="saler">
          <el-input v-model="form.saler" placeholder="请输入销售员" />
        </el-form-item>
        <el-form-item label="销售类型" prop="sale_type">
          <el-input v-model="form.sale_type" placeholder="请输入销售类型" />
        </el-form-item>
        <el-form-item label="收款类型" prop="receipt_type">
          <el-input v-model="form.receipt_type" placeholder="请输入收款类型" />
        </el-form-item>
        <el-form-item label="发票类型" prop="invoice_type">
          <el-input v-model="form.invoice_type" placeholder="请输入发票类型" />
        </el-form-item>
        <el-form-item label="合同编号" prop="contract">
          <el-input v-model="form.contract" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="发票代码" prop="invoice_code">
          <el-input v-model="form.invoice_code" placeholder="请输入发票代码" />
        </el-form-item>
        <el-form-item label="发票号码" prop="invoice_value">
          <el-input v-model="form.fphm" placeholder="请输入发票号码" />
        </el-form-item>
        <el-form-item label="物料编码" prop="wlbm">
          <el-input v-model="form.wlbm" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="盘号" prop="ph">
          <el-input v-model="form.ph" placeholder="请输入盘号" />
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-input v-model="form.count" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="服务起始日" prop="sdate">
          <el-input v-model="form.sdate" placeholder="请输入服务起始日" />
        </el-form-item>
        <el-form-item label="服务结束日" prop="edate">
          <el-input v-model="form.edate" placeholder="请输入服务结束日" />
        </el-form-item>
        <el-form-item label="大类" prop="catory_one">
          <el-input v-model="form.catory_one" placeholder="请输入大类" />
        </el-form-item>
        <el-form-item label="中类" prop="catory_two">
          <el-input v-model="form.catory_two" placeholder="请输入中类" />
        </el-form-item>
        <el-form-item label="小类" prop="catory_three">
          <el-input v-model="form.catory_three" placeholder="请输入小类" />
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="单位" prop="cust_name">
          <el-input v-model="form.cust_name" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="客户分机号" prop="khfjh">
          <el-input v-model="form.khfjh" placeholder="请输入客户分机号" />
        </el-form-item>
        <el-form-item label="行号" prop="hh">
          <el-input v-model="form.hh" placeholder="请输入行号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

<Post>

</Post>
  </div>
</template>

<script>
import { listPost, getPost, delPost, addPost, updatePost } from "@/api/system/post";

export default {
  name: "Post",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 岗位表格数据
      postList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      makeout: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postCode: undefined,
        postName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      // rules: {
      //   postName: [
      //     { required: true, message: "岗位名称不能为空", trigger: "blur" }
      //   ],
      //   postCode: [
      //     { required: true, message: "岗位编码不能为空", trigger: "blur" }
      //   ],
      //   postSort: [
      //     { required: true, message: "岗位顺序不能为空", trigger: "blur" }
      //   ]
      // }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询岗位列表 */
    getList() {
      this.loading = true;
      listPost(this.queryParams).then(response => {
        this.postList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        postId: undefined,
        postCode: undefined,
        postName: undefined,
        postSort: 0,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.postId)
      this.makeout = selection.map(item => item.sh)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增发票信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const postId = row.postId || this.ids
      getPost(postId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改岗位";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.postId != undefined) {
            updatePost(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPost(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.postId || this.ids;
      this.$modal.confirm('是否确认删除岗位编号为"' + postIds + '"的数据项？').then(function() {
        return delPost(postIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 开票按钮操作*/
    invoice(row){
      const sh = this.makeout.join(',');
      this.$modal.confirm('是否提交审核？').then(function() {
        return delPost(sh);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("开票成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/post/export', {
        ...this.queryParams
      }, `post_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
