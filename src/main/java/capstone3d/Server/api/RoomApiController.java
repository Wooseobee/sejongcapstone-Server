package capstone3d.Server.api;

import capstone3d.Server.domain.Room;
import capstone3d.Server.domain.User;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.repository.RoomRepository;
import capstone3d.Server.response.AllResponse;
import capstone3d.Server.response.StatusMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RoomApiController {

    private final RoomRepository roomRepository;

    @GetMapping("/room/{id}")
    public AllResponse RoomUrl(@PathVariable("id") Long id) {
        Room findRoom = roomRepository
                .findById(id)
                .orElseThrow(()-> new BadRequestException(StatusMessage.Not_Found_Room));
        User user = findRoom.getUser();
        String business_name = user.getBusiness_name();
        String phone = user.getPhone();
        return new AllResponse(StatusMessage.Get_Room.getStatus(), StatusMessage.Get_Room.getMessage(), 1, new RoomUrlDto(findRoom.getName(), findRoom.getEmpty_room_url(), findRoom.getFull_room_url(), findRoom.getRoom_img_url(), findRoom.getX(), findRoom.getY(), findRoom.getZ(), business_name, phone));
    }

    @Data
    static class RoomUrlDto {
        private String name;
        private String empty_room_url;
        private String full_room_url;
        private String room_img_url;
        private double x;
        private double y;
        private double z;
        private String business_name;
        private String phone;

        public RoomUrlDto(String name, String empty_room_url, String full_room_url, String room_img_url, double x, double y, double z, String business_name, String phone) {
            this.name = name;
            this.empty_room_url = empty_room_url;
            this.full_room_url = full_room_url;
            this.room_img_url = room_img_url;
            this.x = x;
            this.y = y;
            this.z = z;
            this.business_name = business_name;
            this.phone = phone;
        }
    }
}