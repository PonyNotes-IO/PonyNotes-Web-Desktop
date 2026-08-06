<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="平台" prop="platform">
        <el-select v-model="queryParams.platform" placeholder="请选择平台" clearable>
          <el-option
            v-for="item in platformOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['xmbj:downloadLink:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['xmbj:downloadLink:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['xmbj:downloadLink:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xmbj:downloadLink:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="downloadLinkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="60" />
      <el-table-column label="平台" align="center" prop="platform">
        <template slot-scope="scope">
          <span>{{ getPlatformLabel(scope.row.platform) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="架构标签" align="center" prop="archLabel" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.archLabel">{{ scope.row.archLabel }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="架构描述" align="center" prop="archDesc" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.archDesc">{{ scope.row.archDesc }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="version" width="100" />
      <el-table-column label="下载链接" align="center" prop="downloadUrl" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-link v-if="scope.row.downloadUrl" type="primary" :href="scope.row.downloadUrl" target="_blank">
            {{ scope.row.downloadUrl }}
          </el-link>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sortOrder" width="60" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'info'" size="small">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="operatorName" width="100" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xmbj:downloadLink:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xmbj:downloadLink:remove']"
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

    <!-- 添加或修改下载链接对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="平台" prop="platform">
          <el-select v-model="form.platform" placeholder="请选择平台" style="width: 100%">
            <el-option
              v-for="item in platformOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="架构标签" prop="archLabel">
          <el-input v-model="form.archLabel" placeholder="如：Intel 芯片版 (x86)，仅 macOS 等多架构平台需要填写" />
        </el-form-item>
        <el-form-item label="架构描述" prop="archDesc">
          <el-input v-model="form.archDesc" placeholder="如：适用于 Intel 处理器的 Mac" />
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="如：1.0.0" />
        </el-form-item>
        <el-form-item label="下载链接" prop="downloadUrl">
          <el-input v-model="form.downloadUrl" placeholder="请输入下载链接，如 https://example.com/downloads/PonyNotes-1.0.0.dmg" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" :max="9999" controls-position="right" />
          <span style="margin-left: 8px; color: #999; font-size: 12px;">数字越小越靠前</span>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDownloadLink, getDownloadLink, delDownloadLink, addDownloadLink, updateDownloadLink } from "@/api/xmbj/downloadLink"

export default {
  name: "DownloadLink",
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
      // 下载链接表格数据
      downloadLinkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 平台选项（与 xmbj-www-ui download.vue 保持一致）
      platformOptions: [
        { value: 'Windows', label: 'Windows' },
        { value: 'macOS', label: 'macOS' },
        { value: 'Android/Pad', label: 'Android/Pad' },
        { value: 'iPhone/iPad', label: 'iPhone/iPad' }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        platform: null,
        version: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        platform: [
          { required: true, message: "平台不能为空", trigger: "change" }
        ],
        downloadUrl: [
          { required: true, message: "下载链接不能为空", trigger: "blur" },
          { type: 'url', message: '请输入合法的 URL', trigger: 'blur' }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询下载链接列表 */
    getList() {
      this.loading = true
      listDownloadLink(this.queryParams).then(response => {
        this.downloadLinkList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 平台值转标签
    getPlatformLabel(value) {
      const item = this.platformOptions.find(it => it.value === value)
      return item ? item.label : value
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        platform: null,
        archLabel: null,
        archDesc: null,
        version: null,
        downloadUrl: null,
        sortOrder: 0,
        status: 1,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增下载链接"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getDownloadLink(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改下载链接"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDownloadLink(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDownloadLink(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除下载链接编号为"' + ids + '"的数据项？').then(function() {
        return delDownloadLink(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xmbj/downloadLink/export', {
        ...this.queryParams
      }, `downloadLink_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
