// 搜索条件
export const searchItems = [
    {
        label: '表名',
        value: 'tableName',
        type: 'input',
        placeholder: '表名'
    }
]

// 功能按钮
export const funcs = ['生成JPA模板', '生成Mybatis模板'];

// 表格项
export const tableColumns = [
    { label: '表名', value: 'table' }
]

// 提交参数格式
export const params = {
    "page": 1,
    "pageSize": 10,
    "params": { "name": "j", "age": 22 },//参数
    "operators": { "name": "like", "age": "=" }, //查询方式
    "sortVo": {
        "direction": "ASC",//排序方式 （desc asc 两种）
        "orders": ["name", "age"]//排序字段，可以传多个
    }
}

export const getParams = ['name', 'age']
// 生成模板参数格式
export const postParams = {
    table: 'student',
    sort: "desc",
    data: [
        { label: 'name', operators: 'like', sortable: true },
        { label: 'age', operators: '=', sortable: false }
    ]
}
export class PostParams {
    constructor(params, tableName) {
        this.sort = null;
        this.data = [];
        this.table = tableName;
        params.map(item => {
            this.data.push({ label: item, operators: null, sortable: false })
        })
    }
}