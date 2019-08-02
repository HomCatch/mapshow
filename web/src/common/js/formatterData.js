
// export const formatterData = data => {
//     const formatterArray = [];
//     data.forEach((item) => {
//         if (item.parentId === 0) {
//             formatterArray.push(item);
//             item.flag = true    // 将该项标记为需删除
//         }
//     })
//     data = data.filter(item => !item.flag)  // 删除data中被标记的项
//     console.log('data', data)
//     console.log('formatterArray', formatterArray)

//     a(formatterArray, data);
//     function a(formatterArray, data) {
//         formatterArray.forEach(item1 => {
//             item1.children = []
//             data.forEach(item2 => {
//                 if (item2.parentId === item1.id) {
//                     item1.children.push(item2);
//                     item2.flag = true
//                 }
//             })
//             data = data.filter(item => !item.flag)
//         })
//         console.log('data1', data)
//         console.log('formatter1', formatter)
//     }
// }

export const formatterData = function (data, key = "deptId") {
    let topId = 100;
    data.forEach(item => {
        topId = item.parentId < topId ? item.parentId : topId
    })
    data.forEach(item => {
        let parentId = item.parentId;
        if (parentId === topId) {
            //是根元素的hua ,不做任何操作
        } else {
            //如果item是子元素的话 ,把item扔到他的父亲的child数组中.
            data.forEach(d => {
                if (d[key] === parentId) {
                    let childArray = d.children;
                    if (!childArray) {
                        childArray = []
                    }
                    childArray.push(item);
                    d.children = childArray;
                }
            })
        }
    });
    //去除重复元素
    data = data.filter(item => item.parentId === topId);
    console.log(data)
    return data;
}
// export const formatterData = function (data, key = "deptId") {
//     data.forEach(item => {
//         let parentId = item.parentId;
//         if (parentId === 0) {
//             //是根元素的hua ,不做任何操作
//         } else {
//             //如果item是子元素的话 ,把item扔到他的父亲的child数组中.
//             data.forEach(d => {
//                 if (d[key] === parentId) {
//                     let childArray = d.children;
//                     if (!childArray) {
//                         childArray = []
//                     }
//                     childArray.push(item);
//                     d.children = childArray;
//                 }
//             })
//         }
//     });
//     //去除重复元素
//     data = data.filter(item => item.parentId === 0);
//     console.log(data)
//     return data;
// }