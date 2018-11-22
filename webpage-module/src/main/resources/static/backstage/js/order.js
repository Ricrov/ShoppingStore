var currentPage = 1
var count = 6
var inquire;
var pagin;
$(function () {


    getRemoteOrder(currentPage,count,inquire)

    $('#prev').click(function () {
        --currentPage;
        if (currentPage < 1) {
            currentPage = 1
        }
        getRemoteAdmin(currentPage, count, inquire)
    })


    $('#next').click(function () {
        ++currentPage;
        if (currentPage > pagin) {
            currentPage = 1;
        }
        getRemoteAdmin(currentPage, count, inquire)
    })



    function getRemoteOrder(page, count, inquireMsg){
        page = +page <= 0 ? 1:page;
        count = +count <= 0 ? 6 : count;

        var params = {
            page: page,
            count: count,
            inquireMsg : inquireMsg
        }
    }




    $('#btn-13').click(function () {

        inquire = $('#inquire').val()

        getRemoteAdmin(currentPage, count, inquire)
    })



    function handlerResponse(resp) {
        if(resp.status === 200){
            var totalCount = resp.data.totalCount;
            var pages = Math.ceil(totalCount/count);
            drawPageIndicator(pages);
            var items = resp.data.items;
            putItemsInTable(items);
        }
    }

    function drawPageIndicator(pages) {
        console.log('pages' + pages)
        var ul = $('#page')
        ul.empty();

        for (var i = 1; i <= pages; i++) {
            var li = $('<li style="float: left"></li>');
            // console.log(li)

            if (currentPage == i) {
                li.addClass('current_page');
            }

            var a = $('<a></a>');
            // 给a标签添加属性,这条属性的意思为点击链接后不进行页面上的跳转.
            a.attr('href', 'javascript:void(0)');
            a.text(i);

            a.click(onIndicatorClick)

            li.append(a);
            ul.append(li);
        }
    }

    function onIndicatorClick() {
        currentPage = +$(this).text();

        getRemoteAdmin(currentPage, count);
    }



    function putItemsInTable(items) {
        $.get('/order/findAll', params,handlerResponse)
        var tbody = $('#tb-2538292730');
        tbody.empty();

        for (var item of items){
            var tr1 = $('<tr class="tr-th"></tr>')

            var td1 = $('<td colspan="6"></td>')

            var span = $('<span class="tcol1">订单编号:</span>')
            var a = $('<a name="orderIdLinks" id="idUrl2538292730" target="_blank"\n' +
                'href="http://order.jd.com/normal/item.action?orderid=2538292730&amp;PassKey=769448C6BA99F1ADA8244BAE7BC60580"\n' +
                'clstag="click|keycount|orderinfo|order_num"></a>').text(item.orderId)
            span.append(a)
            var span1 = $('<span class="tcol2">shop</span>')
            var span2 = $('<span class="tcol3"></span>')
            var a1 = $('<a class="btn-im" onclick="getPamsForChat()" href="#none"\n' +
                'title="联系客服"></a>')
            span2.append(a1)
            td1.append(span).append(span1).append(span2)
            tr1.append(td1)
            var tr2 = $('<tr id="track2538292730" oty="0,1,70" class="tr-td"></tr>')
            var td2 = $('<td></td>')
            var div1 = $('<div class="img-list"></div>')
            var a2 = $('<a href="http://item.jd.com/1113410.html" class="img-box"clstag="click|keycount|orderinfo|order_product" target="_blank"></a>')
            var divimg = $('<img title="TP-LINK TL-WR885N 450M无线路由器（白）" width="50" height="50"src="../../frontstage/images/rBEhV1NXYLYIAAAAAADGO8-wV-UAAMfdgNi-BAAAMZT367.jpg"class="err-product">')
            a2.append(divimg)
            var a3 = $('<a href="http://item.jd.com/1222567.html" class="img-box"clstag="click|keycount|orderinfo|order_product" target="_blank"></a>')
            var divimg1 = $('<img title="RND 扁平网线 超高速六类（CAT.6)  超薄扁平  线长为1米5 炫白色" width="50"height="50" src="../../frontstage/images/541fdd63Nab1b3c63.jpg"class="err-product">')
            a3.append(divimg1)
            div1.append(a2).append(a3)
            td2.append(div1)
            var td3 = $('<td></td>')
            var divname = $('<div class="u-name"></div>').test(item.receiverName)
            td3.append(divname)
            var td4 = $('<td></td>')
            var span3 = $('<span></span>').test(item.payment)
            var span4 = $('<span></span>').test(item.paymentType)
            td4.append(span3).append(span4)
            var  td5 = $('<td></td>')
            var span5 = $('<span class="ftx-03"></span>').test(item.createTime)
            var input1 = $('<input type="hidden" id="datasubmit-2538292730" value="2014-10-20 22:30:49">')
            td5.append(span5).append(input1)
            var td6 = $('<td></td>')
            var span6 = $('<span class="ftx-03"></span>').test(item.status)
            td6.append(span6)
            var td7 = $('<td id="operate2538292730" class="order-doi" width="100"></td>')
            var span7 = $('<span id="pay-button-2538292730" state=""></span>')
            var a4 = $('<a target="_blank" href="http://order.jd.com/normal/item.action?orderid=2538292730&amp;PassKey=769448C6BA99F1ADA8244BAE7BC60580" clstag="click|keycount|orderinfo|order_check"></a>')
            var span8 = $('<span id="order_comment"></span>')
            var span9 = $('<span class="pop-recycle-a"></span>')
            var a5 = $('<a href="javascript:void(0)" clstag="click|keycount|orderinfo|order_del" onclick="ensureMoveOrderToRecycle(2538292730,\'397FF574E089D5409E6CC8EF67129D65\');">删除</a>')
            span9.append(a5)
            var span10 = $('<span id="doi2538292730"></span>')
            var a6 = $('<a href="http://club.jd.com/JdVote/TradeComment.aspx?ruleid=2538292730&amp;ot=0&amp;payid=1&amp;shipmentid=70" target="_blank" clstag="click|keycount|orderinfo|order_comment">评价晒单</a>')
            span10.append(a6)
            var a7 = $('<a  href="http://myjd.jd.com/repair/ordersearchlist.action?searchString=2538292730" target="_blank" clstag="click|keycount|orderinfo|order_repair">申请返修/退换货</a>')
            var a8 = $('<a class="btn-again" clstag="click|keycount|orderlist|buy" href="http://cart.jd.com/cart/dynamic/reBuyForOrderCenter.action?wids=1113410,1222567&amp;nums=1,1&amp;rid=1419846299535" target="_blank">还要买</a>')
            td7.append(span7).append(a4).append(span8).append(span9).append(span10).append(a7).append(a8)
            tr2.append(td2).append(td3).append(td4).append(td5).append(td6).append(td7)
            tbody.append(tr1).append(tr2)


        }
    }
})