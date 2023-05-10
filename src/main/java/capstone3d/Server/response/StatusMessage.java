package capstone3d.Server.response;

import lombok.Getter;

@Getter
public enum StatusMessage {
    Sign_Up_Success(200, "회원가입에 성공하였습니다."),
    Login_Success(200, "로그인에 성공하였습니다."),
    Reissue_Token_Success(200, "Access Token 재발급에 성공하였습니다."),
    Withdraw_Success(200, "회원탈퇴에 성공하였습니다."),
    Update_Success(200, "회원정보 수정에 성공하였습니다."),
    Get_RoomList(200, "방 리스트 조회 성공"),
    Get_Room(200, "방 리스트 조회 성공"),
    Get_FurnitureList(200, "가구 리스트 조회 성공"),
    User_Upload_Success(200, "유저 업로드에 성공하였습니다."),
    Admin_Upload_Success(200, "관리자 업로드에 성공하였습니다."),
    Get_RoomList_Fail(201, "등록된 방이 없습니다."),
    Get_FurnitureList_Fail(202, "등록된 가구가 없습니다."),

    Unauthorized(401, "토큰 인증에 실패하였습니다."),
    Refresh_Token_Unauthorized(402, "Refresh 토큰 인증에 실패하였습니다."),
    Forbidden(403, "권한이 없습니다."),
    Not_Found_User(404, "회원이 존재하지 않습니다."),
    Not_Found_Room(404, "해당 방을 찾을 수 없습니다."),
    Login_Fail(405, "아이디 혹은 비밀번호를 확인해주세요."),
    Email_Duplicated(406, "이미 존재하는 아이디입니다."),
    Nickname_Duplicated(407, "이미 존재하는 닉네임입니다."),
    Not_Match_Password(408, "비밀번호가 일치하지 않습니다."),
    Request_Error(409, "요청 형식이 잘못되었습니다."),
    SignUp_Request_Error(410, "회원가입 요청 형식이 잘못되었습니다."),
    Login_Request_Error(411, "로그인 요청 형식이 잘못되었습니다."),
    Update_Request_Error(412, "회원정보수정시 비밀번호는 필수입니다."),
    Update_Request_Not_Match_Password(413, "수정 비밀번호가 일치하지 않습니다."),
    UploadFile_format_Error(414, "업로드 파일 형식이 잘못되었습니다."),
    Upload_Error(415, "업로드 요청 형식이 잘못되었습니다."),
    Admin_UploadFile_format_Error(416, "괸리자 업로드 파일 형식이 잘못되었습니다."),
    Admin_Upload_Error(417, "관리자 업로드 요청 형식이 잘못되었습니다.");

    private int status;
    private String message;

    StatusMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }
}