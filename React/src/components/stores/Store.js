import { observable, makeObservable, action } from 'mobx';

const store = observable({
    memberName : sessionStorage.getItem("memberName") || '',
    memberType : sessionStorage.getItem("memberType") || '',
    accessToken : sessionStorage.getItem('access_token') || '',
    refreshToken : sessionStorage.getItem("refresh_token") || '',
    memberId : sessionStorage.getItem('memberId') || '',

  setUserInfo(data) {
    this.memberName = data.memberName;
    this.memberType = data.memberType;
    this.accessToken = data.accessToken;
    this.refreshToken = data.refreshToken;
    this.memberId = data.memberId;
  },
  setMemberName(data) {
    this.memberName = data.memberName;
  },
  logOut() {
    this.memberName = null;
    this.memberType = null;
    this.accessToken = null;
    this.refreshToken = null;
    this.memberId = null;
  }
});


export { store };