import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {path: '/', redirect: '/login'},
        {
            path: '/manager', component: () => import('../views/Manager.vue'), children: [
                // meta 补充路由对象
                {path: 'home', name: 'home', meta: {title: '主页'}, component: () => import('../views/Home.vue')},
                {path: 'text', name: 'text', meta: {title: '测试'}, component: () => import('../views/Text.vue')},
                {path: 'data', name: 'data', meta: {title: '数据展示页面'}, component: () => import('../views/Data.vue')},
                {path: 'person', name: 'person', meta: {title: '个人消息页面'},component: () => import('../views/person.vue') },
                {path: 'employee', name: 'employee', meta: {title: '员工信息'},component: () => import('../views/Employee.vue') },
                {path: 'admin', name: 'admin', meta: {title: '管理员信息'},component: () => import('../views/Admin.vue') },
                {path: 'password', name: 'password', meta: {title: '修改密码'},component: () => import('../views/Password.vue') },
                {path: 'article', name: 'article', meta: {title: '文章'},component: () => import('../views/Article.vue') },

            ]
        },
         {path: '/login', name: 'login', meta: {title: '登录'}, component: () => import('../views/Login.vue')},
        {path: '/register', name: 'register', meta: {title: '注册'}, component: () => import('../views/Register.vue')},
         {path: '/404', name: 'NotFound', meta: {title: '404找不到页面'}, component: () => import('../views/404.vue')},
         {path: '/:pathMatch(.*)', redirect: '/404'},
    ]
})

// router.beforeEach((to, from, next) => {
//   // console.log(to, from);
//   // console.log(to.meta.title);
//   document.title = to.matched[0].meta.title;
//   next(); // 调用 next() 才会进入下一个网页
// })
router.beforeEach((to, from, next) => {
    const matched = to.matched;
    // 从后往前找，找到第一个有 meta.title 的路由
    const routeWithTitle = matched.slice().reverse().find(m => m.meta?.title);

    if (routeWithTitle) {
        document.title = routeWithTitle.meta.title;
    } else {
        document.title = '博客系统'; // 默认标题
    }

    next();
});


export default router
