var utils = {};
utils.loading = function () {
    NProgress.start();
    setTimeout(function () {
        NProgress.set(0.5)
    }, 200);
    setTimeout(function () {
        NProgress.set(0.8)
    }, 300);
    setTimeout(function () {
        NProgress.set(1)
    }, 400);
};