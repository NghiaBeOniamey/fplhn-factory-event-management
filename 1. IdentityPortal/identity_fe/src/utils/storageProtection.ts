import * as CryptoJS from "crypto-js";

const secretKey = process.env.REACT_APP_SECRET_KEY || "pFBSx4[O}|7.6h7";

export function encrypt(objToEn: any) {
  const strToEn = JSON.stringify(objToEn);
  return CryptoJS.AES.encrypt(strToEn, secretKey).toString();
}

export function decrypt(strToDe: string) {
  if (!strToDe || strToDe.length === 0) {
    console.error("Invalid string to decrypt");
    return null;
  }
  try {
    const decryptedString = CryptoJS.AES.decrypt(strToDe, secretKey).toString(
      CryptoJS.enc.Utf8
    );
    return JSON.parse(decryptedString);
  } catch (error) {
    console.error("Decryption error:", error);
    return null;
  }
}
