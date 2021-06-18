<template>
  <div class="q-pa-xl">
    <!-- 按鈕區塊 -->
    <div class="row q-col-gutter-xs">
      <div class="col-12 col-md-2">
        <q-btn
          label="SEARCH EMPLOYEES"
          class="full-width"
          @click="callGetEmployees();"
        />
      </div>
      <div class="col-12 col-md-2">
        <q-btn
          label="SEARCH EMPLOYEESLIST"
          class="full-width"
          @click="callGetEmployeesList();"
        />
      </div>
      <div class="col-12 col-md-2">
        <q-btn label='SAVE EMPLOYEES' class="full-width" @click='editDialog=true'/>
      </div>
      <div class="col-12 col-md-2 offset-md-4">
        <q-btn label='UPDATE EMPLOYEES' class="full-width" @click='updateOpenDialog' v-show="selected.length!=0"/>
      </div>
      <div class="col-12 col-md-2">
        <q-btn label='DELETE EMPLOYEES' class="full-width" @click='callDeleteEmployee();callGetEmployees();' v-show="selected.length!=0"/>
      </div>
    </div>
    <q-separator spaced />
    <!-- 輸入區塊 -->
    <div class="row q-col-gutter-xs">
      <div class="col-12 col-md-6">
        <q-input
          label="First Name"
          filled
          clearable
          v-model="request.getEmployeesList.params.firstName"
        >
        </q-input>
      </div>
      <div class="col-12 col-md-6">
        <q-input
          label="last nameXXX"
          filled
          clearable
          v-model="request.getEmployeesList.params.lastName"
        ></q-input>
      </div>
      <div class="col-12 col-md-6">
        <q-input
          label="Email"
          filled
          clearable
          type="email"
          v-model="request.getEmployeesList.params.email"
        >
        </q-input>
      </div>
      <div class="col-12 col-md-6">
        <q-input
          label="Phone Number"
          filled
          v-model="request.getEmployeesList.params.phoneNumber"
          clearable
        />
      </div>
      <div class="col-12 col-md-6">
        <q-input
          label="Hire Date From"
          filled
          clearable
          type="date"
          stack-label
          v-model="request.getEmployeesList.params.hireDateFrom"
        >
        </q-input>
      </div>
      <div class="col-12 col-md-6">
        <q-input
          label="Hire Date To"
          filled
          clearable
          type="date"
          stack-label
          v-model="request.getEmployeesList.params.hireDateTo"
        >
        </q-input>
      </div>
      <div class="col-12 col-md-6">
        <q-input
          label="Salary From"
          filled
          clearable
          type="number"
          v-model="request.getEmployeesList.params.salaryFrom"
        >
        </q-input>
      </div>
      <div class="col-12 col-md-6">
        <q-input
          label="Salary To"
          filled
          clearable
          type="number"
          v-model="request.getEmployeesList.params.salaryTo"
        >
        </q-input>
      </div>
      <div class="col-12 col-md-6">
        <q-select
          label="Job Title"
          filled
          clearable
          :options="jobs"
          map-options
          emit-value
          v-model="request.getEmployeesList.params.jobId"

        >
        </q-select>
      </div>
      <div class="col-12 col-md-6">
        <q-select
          label="Departmente Names"
          filled
          clearable
          :options="departments"
          map-options
          emit-value
          multiple
          v-model="format.departmentIds"
          @input="formatDepartmentIds($event)"
        >
        </q-select>
      </div>
    </div>
    <q-separator spaced />
    <!-- 顯示區塊 -->
    <!-- <div class="row"> -->
      <!-- <div class="col"> -->
        <!-- <q-table> -->
      <!-- </div> -->
    <!-- </div> -->
    <div class="row">
      <div class="col">
        <q-table
          title="Employee"
          :data="employees"
          :columns="columns"
          :pagination.sync="pagination"
          @request="alterPagination"
          row-key="employeeId"
          selection="single"
          :selected.sync="selected"
        >
          <template v-slot:body-cell-jobTitle="props">
            <q-td :props="props">
              {{ getLabel('jobs', props.row.jobId) }}
            </q-td>
          </template>

          <template v-slot:body-cell-departmentName="props">
            <q-td :props="props">
              {{ getLabel('departments', props.row.departmentId) }}
            </q-td>
          </template>
        </q-table>
      </div>
    </div>
    <q-dialog v-model="editDialog" persistent>
      <q-card style="width: 700px; max-width: 80vw;">
        <!-- 標題 -->
        <q-card-section>
          <span class="text-h6">Edit Employee Information</span>
        </q-card-section>
        <!-- 輸入欄位 -->
        <q-card-section>
          <q-input
            v-model="request.saveEmployee.data.employeeId"
            label="Employee ID"
            disable
            v-show="request.saveEmployee.data.employeeId"
          />
          <q-input
            v-model="request.saveEmployee.data.firstName"
            label="First Name"
            clearable
          />
          <q-input
            v-model="request.saveEmployee.data.lastName"
            label="Last Name"
            clearable
          />
          <q-input
            v-model="request.saveEmployee.data.email"
            label="Email"
            clearable
          />
          <q-input
            v-model="request.saveEmployee.data.phoneNumber"
            label="Phone Number"
            clearable
          />
          <q-select
            v-model="request.saveEmployee.data.jobId"
            label="Job Title"
            :options="jobs"
            emit-value
            map-options
            clearable
          />
          <q-select
            v-model="request.saveEmployee.data.departmentId"
            label="Department Name"
            :options="departments"
            emit-value
            map-options
            clearable
          />
          <q-input
            v-model="request.saveEmployee.data.hireDate"
            label="Hire Date"
            type="date"
            stack-label
            clearable
          />
          <q-input
            v-model="request.saveEmployee.data.salary"
            label="Salary"
            type="number"
            clearable
          />
        </q-card-section>
        <!-- 功能按鈕 -->
        <q-card-actions align="right">
          <q-btn
            flat
            label="Cancel"
            color="red"
            v-close-popup
            @click="newEmployee={}"
          />
          <q-btn
            flat
            label="Confirm"
            color="primary"
            v-close-popup
            @click="callSaveEmployee();callGetEmployees();"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      request: {
        getEmployees: {
          method: 'GET',
          url: '/api/employees',
          params: {}
        },
        getEmployeesList: {
          method: 'GET',
          url: '/api/employees/list',
          params: {}
        },
        getJobs: {
          method: 'GET',
          url: '/api/jobsLabelValues'
        },
        getDepartments: {
          method: 'GET',
          url: '/api/departmentsLabelValues'
        },
        saveEmployee: {
          method: 'GET',
          url: '/api/employee/save',
          data: {}
        },
        deleteEmployee: {
          method: 'POST',
          url: '/api/employee/delete',
          data: {}
        }
      },
      editDialog: false,
      columns: [
        // { name: 'employeeId', label: 'Employee ID', field: row => row.employeeId },
        { label: 'Employee ID', field: (row) => row.employeeId },
        { label: 'First Name', field: (row) => row.firstName },
        { label: 'Last Name', field: (row) => row.lastName },
        { label: 'Email', field: (row) => row.email },
        { label: 'Phone Number', field: (row) => row.phoneNumber },
        { label: 'Salary', field: (row) => row.salary },
        { label: 'Hire Date', field: (row) => row.hireDate },
        // { label: 'Job ID', field: (row) => row.jobId },
        // { label: 'Department ID', field: (row) => row.departmentId }
        {
          name: 'jobTitle',
          label: 'Job Title',
          field: (row) => row.jobId
        },
        {
          name: 'departmentName',
          label: 'Department Name',
          field: (row) => row.departmentId
        }
      ],
      pagination: {},
      employees: [],
      jobs: [],
      departments: [],
      format: {
        departmentIds: []
      },
      selected: []
    }
  },
  created () {
    this.callGetJobs()
    this.callGetDepartments()
  },
  methods: {
    reset () {
      this.pagination.rowsNumber = 0
      this.pagination.rowsPerPage = 0
      this.pagination.page = 0
      console.log('reset')
      this.request.getEmployees.params.page = 0
    },
    async callGetEmployees () {
      // this.reset()
      // 回呼資料
      const response = await axios(this.request.getEmployees)
      // 回傳後更新 employees 資料
      this.employees = response.data.content
      // 回傳後更新 pagination 資料
      this.pagination.rowsNumber = response.data.totalElements
      console.log(this.pagination.rowsNumber)
      this.pagination.rowsPerPage = response.data.size
      this.pagination.page = response.data.number + 1
      this.selected = []
      this.request.getEmployees.params.page = 0
      console.log('c')
    },
    async callGetEmployeesList () {
      this.pagination = {
        page: 1,
        rowsPerPage: 20
      }
      // this.reset()
      // 回呼資料
      const response = await axios(this.request.getEmployeesList)
      // 回傳後更新 employees 資料
      console.log(response.data)
      this.employees = response.data
      // 回傳後更新 pagination 資料
      // this.pagination.rowsNumber = response.data.totalElements
      // console.log(this.pagination.rowsNumber)
      // this.pagination.rowsPerPage = response.data.size
      // this.pagination.page = response.data.number + 1
      this.selected = []
      // this.request.getEmployeesList.params.page = 0
      console.log('c')
    },
    async callGetJobs () {
      const response = await axios(this.request.getJobs)
      this.jobs = response.data
    },
    async callGetDepartments () {
      const response = await axios(this.request.getDepartments)
      this.departments = response.data
    },
    formatDepartmentIds (args) {
      const result = args !== null ? args.toString() : null
      this.request.getEmployeesList.params.departmentIds = result
    },
    async alterPagination (args) {
      // 觸發後改寫回呼參數
      this.request.getEmployees.params.page = args.pagination.page - 1
      this.request.getEmployees.params.size = args.pagination.rowsPerPage
      await this.callGetEmployees()
      console.log('a')
    },
    getLabel (source, id) {
      return !id ? '' : this[source].find(each => each.value.toString() === id.toString()).label
    },
    async callSaveEmployee () {
      try {
        const response = await axios(this.request.saveEmployee)
        alert(`Employee ID: ${response.data.employeeId}\nName: ${response.data.firstName} ${response.data.lastName}\nhas been saved successfully!`)
      } catch (error) {
        alert('Save Employee Faild')
        console.log(error)
      }
      this.request.saveEmployee.data = {}
    },
    async callDeleteEmployee () {
      this.request.deleteEmployee.data.employeeId = this.selected[0].employeeId
      try {
        await axios(this.request.deleteEmployee)
        alert(`Employee ID: ${this.request.deleteEmployee.data.employeeId}\nhas been deleted.`)
      } catch (error) {
        alert('Delete Failed')
        console.log(error)
      }
    },
    updateOpenDialog () {
      this.editDialog = true
      this.request.saveEmployee.data = Object.assign({}, this.selected[0])
    }
  }
}
</script>

<style>
</style>
