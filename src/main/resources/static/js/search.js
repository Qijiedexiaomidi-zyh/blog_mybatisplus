new Vue({
    el: '#app',
    data: {
        keyword: '',
        results: []
    },
    methods: {
        searchByKeyword(){
            var keyword = this.keyword;
            console.log(keyword);
            //对接后端接口
            axios.get('elasticsearch/'+keyword+'/1/10').then(response=>{
                console.log(response);
                //绑定数据
                this.results = response.data;
            })
        }
    }
});