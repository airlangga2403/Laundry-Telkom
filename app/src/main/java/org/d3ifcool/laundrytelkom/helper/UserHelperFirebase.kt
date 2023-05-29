package org.d3ifcool.laundrytelkom.helper

class UserHelperFirebase {

    lateinit var email: String
    lateinit var name: String
    lateinit var mobileNum: String
    lateinit var address: String

    constructor(email: String, name: String, mobileNum: String, address: String) {
        this.email = email
        this.name = name
        this.mobileNum = mobileNum
        this.address = address
    }
}