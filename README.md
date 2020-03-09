# clubInformation-management-backend


# 接口文档

## 通用返回结构

```
{
    data:{},
    code: 404,
    message:"not found"
    
}
```

## 获取验证码

method: POST

path: /api/captcha

req：
```
{
   email: "hao.lin@thoughtworks.com"
}
```

res: 

```

```

## 邮箱登陆验证

method: POST

path: /api/email/verify

req:

```
{
    email: "hao.lin@thoughtworks.com"
    captcha: 123456
}
```
res:

```
{
    newUser: true，
    success: true
}
```

## 注册用户

// 用户ID 自己维护还是直接使用微信ID

method: POST

path: /api/sign/up

req:
```
{
    email:"hao.lin@thoughtworks.com",
    userName: "hao.lin",
    weChatNO: "natsuki_",
    photo:"xxx"
}
```

res:
```
{
    id:123
}
```

## 获取用户信息

method: GET

path: /api/user/{userId}

res:
```
{
    id: 568715
    username: "chenjw"
    status: 0
    phone: "15963581687"
    profileImagePath: "/storage/user/568715/chenjw.png"
}
```

## 修改个人信息

method: PUT

path: /api/user

req:
```
{
    weChatNO: "natsuki_",
    userName: "",
    displayName: "hao.lin",
    photo: "",
    cellphone: "",
    address: "",
    //再补充
}
```

res:
```
```

## 获取俱乐部类型
method: GET

path: /api/clubInformation/type

res:

```
[{id:1,name:"运动"}]
```

## 创建俱乐部
method: POST

path: /api/clubInformation

req: 
```
{
    picture: "",
    name:"篮球",
    type:"运动",
    introduction:"",
    address:""
}
```

res:
```

```

## 获取俱乐部申请列表

## 查询用户参与的俱乐部
method: GET

path: /api/club/user/{Id}

res:
```
[
    {
        id: 123,
        picture:"",
        name:"篮球",
        type:"",
        approveStatus:"",
        isManager: false,
        introduction:"",
    }
]
```


## 获取所有俱乐部列表

method: GET

path: /api/club

res:
```
[
    {
        id:"",
        picture:"",
        name:"篮球",
        type:"",
        isManager: false,
        isJoin: true,
        introduction:"",
    },
    {...}
]
```

## 申请加入俱乐部
method：POST

path: /api/club/join

req:
```
{
    userId: 123,
    clubId: 123,
    reason: "爱好篮球",
    cellphone: 12312341234,
    weChatNo: ""
}
```

res:
```
```

## 获取当前用户俱乐部权限

## 俱乐部管理员获取会员加入申请记录

## 审批俱乐部成员
method: PUT

path: /api/club/member

req:
```
{
    recordId: id,
    agree: true,
    reason: "无"
    
}
```

## 俱乐部详情

method: GET

path：/api/club/{id}

res:
```
{
    picture: "xxx/xxx.png",
    name:"篮球",
    type:"运动",
    introduction:"篮球兴趣俱乐部，场地xxx，自由约球",
    address:""
    members: [],
    activities: []
}
```

## 俱乐部成员列表
method：GET

path: /api/club/{clubId}/member

res:
```
[
    {
        photo: "xxx",
        userId: 123,
        displayName: "123"
   },
   {...}
]
```

## 删除俱乐部成员/
method: DELETE

path: /api/club/{clubId}/member/{userId}

res:
```
```

##  退出俱乐部

## 提升管理员
method: POST

path: /api/club/manager

req:
```
{
    clubId: 123,
    userId: 123,
    // 附带当前操作人  
}
```

## 转移管理员权限
method: PUT

path: /api/club/manager

```
{
    origin: 123,
    present: 123
}
```

## 删除管理员

method: DELETE

path: /api/club/manager/{userId}



## 发布活动

method: POST

path: /api/activity

req:
```
{
   
    clubId: 123,
    clubName: "篮球",
    name: "打篮球"，
    picture: "xxx/xxx.png",
    endJoinDate: "2020-4-1",
    startDate: "2020-4-2",
    endDate: "2020-4-2",
    limit: 10,
    description: "希望会唱、跳、rap"，
    open: false,
    thumbsUp: 0
}
```

res:
```
{
    activityId: 123
}
```

## 活动列表
method: GET

path: /api/activity

req: 
```
{
    isRecruiting: true,
}
```

res:
```
[
    {
        id: 123,
        name: "打篮球",
        picture: "xxx/xxx.png",
        description: "",
        status: "RECRUITING",
        isJoin: false,
        thumbsUp: 0
    }
]
```

## 活动详情
method: GET

path: /api/activity/{activityId}

res:
```
{
    id: 123,
    name: "打篮球",
    picture: "xxx/xxx.png",
    description: "",
    status: "RECRUITING",
    isJoin: false,
    joinedUser: [],
    thumbsUp: 0
}
```

## 删除活动
method: DELETE

path: /api/activity/{acitivityId}   cookie操作人


## 俱乐部活动列表

method: GET

path: /api/activity/club/{clubId}

res:
```
[
    {
        id: 123,
        name: "打篮球",
        picture: "xxx/xxx.png",
        description: "",
        status: "RECRUITING",
        thumbsUp: 0
    },
    {
        id: 1233,
        name: "打篮球2",
        picture: "xxx/xxx.png",
        description: "",
        status: "END",
        thumbsUp: 0
    }
]
```

## 我的活动列表

method: GET

path: /api/activity/user/{userId}

res:
```
[
    {
        id: 123,
        name: "打篮球",
        picture: "xxx/xxx.png",
        description: "",
        status: "RECRUITING",
        thumbsUp: 0
    },
    {
        id: 1233,
        name: "打篮球2",
        picture: "xxx/xxx.png",
        description: "",
        status: "END",
        thumbsUp: 0
    }
]
```
