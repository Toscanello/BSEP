export const validName = new RegExp(
    '^[A-Za-z]+$'
 );
 export const validPassword = new RegExp('^(?=.*?[A-Za-z])(?=.*?[0-9]).{6,}$');
 export const validSearch = new RegExp('^[A-Za-z]+$');
 export const validUsername = new RegExp("^[A-Za-z][A-Za-z0-9_]{7,29}$")
