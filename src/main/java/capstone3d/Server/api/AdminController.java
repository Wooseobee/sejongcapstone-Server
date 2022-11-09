package capstone3d.Server.api;

import capstone3d.Server.domain.dto.AdminUploadFileDto;
import capstone3d.Server.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin/upload")
    public String uploadFile(AdminUploadFileDto adminUploadFileDto) throws IOException {

        adminService.saveFile(adminUploadFileDto, adminUploadFileDto.getUserIdentification());

        return "adminUpload";
    }
}
