package com.example.vinid_icecreams.mock

import com.example.vinid_icecreams.model.*

object MockData {
    fun getListStore(): ArrayList<Store> {
        val mListStore = ArrayList<Store>()
        for (i in 1..10) {
            val mMockStore = Store(
                i,
                "Cửa hàng thứ $i",
                "Nhà No9 KĐT, Pháp Vân, Hoàng Ma,Hà Nội",
                "https://f.thuongtruong.com.vn/2018/01/vinmart2-09_50_45_735.jpg?width=420&height=200&mode=crop&anchor=middle",
                123.213,
                312.311,
                312312312312,
                getListCream(),
                37123.312
            )
            mListStore.add(mMockStore)
        }
        return mListStore
    }

    fun getListCream(): ArrayList<IceCream> {
        val mListCream = ArrayList<IceCream>()
        for (i in 1..20) {
            val mMockIcream = IceCream(
                i,
                "Kem dâu",
                "Kem ốc quế",
                getListImageIceCream(),
                100,
                20,
                3.5f,
                31263712836,
                getListComment()
            )
            mListCream.add(mMockIcream)
        }
        return mListCream
    }

    fun getUser(): User {
        return User(
            0,
            "phạm lê duy hùng",
            981438710,
            "hung0894",
            "Hà Nội",
            2000,
            3123124124214
        )
    }

    fun getListComment(): ArrayList<Comment> {
        val mListComment = ArrayList<Comment>()
        for (i in 1..5) {
            val mComment = Comment(
                "Trần dần",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQG1oykVGaj4HQ_U-uCEFbfX_hFQ7QqaNyMWs_pUmGYf5IByGta&s",
                4.5F,
                "Kem rất ngon"
            )
            mListComment.add(mComment)
        }
        return mListComment
    }

    fun getListImageIceCream(): ArrayList<String> {
        val mListImage = ArrayList<String>()
        mListImage.add("https://cdn.apartmenttherapy.info/image/fetch/f_jpg,q_auto:eco,c_fill,g_auto,w_1500,ar_1:1/https%3A%2F%2Fstorage.googleapis.com%2Fgen-atmedia%2F3%2F2014%2F06%2F04a3c9323983d070e1177a0c6300d1afce24ae04.jpeg")
        mListImage.add("https://media.gettyimages.com/photos/ice-cream-picture-id157671800?s=612x612")
        mListImage.add("https://5.imimg.com/data5/MN/OY/MY-5505468/5-500x500.jpg")
        return mListImage
    }

    fun getListEvent(): ArrayList<Event> {
        val mListEvent = ArrayList<Event>()
        for (i in 1..10) {
            var mEvent = Event(
                "https://cdn7.allevents.in/banners/bd285330-055f-11ea-94d7-2faf897ffe8d-rimg-w526-h526-gmir.jpg?v=1573571935"
                , "CHƯƠNG TRÌNH ƯU ĐÃI MUA VÉ THÁNG 12/2019"
            ,"Dành cho khách hàng chưa từng phát sinh thành công giao dịch qua Ví điện tử VinID Pay và mua vé trên ứng dụng VinID, nhận được bộ ưu đãi:\n" +
                        "\n" +
                        "01 mã ưu đãi mua vé CGV với giá 49,000đ – HSD 6 tháng\n" +
                        "02 mã ưu đãi Giảm 50% cho tính năng “Thanh toán” trên ứng dụng VinID khi mua hàng tại hệ thống VinMart+ với giá trị giảm tối đa 25,000đ – HSD 10 ngày\n" +
                        "02 mã ưu đãi Giảm ngay 50,000đ cho hóa đơn điện, nước từ 200,000đ – HSD 10 ngày\n" +
                        "02 mã ưu đãi Giảm 25,000đ cho tính năng “Nạp tiền điện thoại” với hóa đơn từ 100,000đ – HSD 10 ngày"
            )
            mListEvent.add(mEvent)
        }
        return mListEvent
    }

}