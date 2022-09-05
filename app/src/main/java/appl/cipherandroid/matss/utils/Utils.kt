package appl.cipherandroid.matss.utils

import appl.cipherandroid.matss.algorithms.AESCrypt
import appl.cipherandroid.matss.constants.Constants

object Utils {

    fun toEncrypt(str: String) : String {
        try {
            return AESCrypt.encrypt(Constants.CIPHER_KEY, str)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return "An error occurred!"
    }

    fun toDecrypt(str: String) : String {
        try {
            return AESCrypt.decrypt(Constants.CIPHER_KEY, str)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return "An error occurred!"
    }
}