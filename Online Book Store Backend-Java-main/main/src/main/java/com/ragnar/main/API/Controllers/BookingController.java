package com.ragnar.main.API.Controllers;

import com.ragnar.main.API.DTOs.Booking.BookItemDTO;
import com.ragnar.main.API.DTOs.Booking.ReturnItemDTO;
import com.ragnar.main.Application.IServices.IBookingService;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Helpers.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/Booking")
@RequiredArgsConstructor
public class BookingController {
    private final IBookingService bookingService;
    private final AuthUtil authUtil;

    @PostMapping("book")
    public ResponseEntity<?> BookItem(@RequestBody BookItemDTO model, Principal principal) {
        model.setUserId(authUtil.getUserIdFromPrincipal(principal));
        var res = bookingService.BookItem(model);
        return ResponseEntity.ok(res);
    }

    @PostMapping("return")
    public ResponseEntity<?> ReturnItem(@RequestBody ReturnItemDTO model, Principal principal) {
        model.setUserId(authUtil.getUserIdFromPrincipal(principal));
        var res = bookingService.ReturnItem(model);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<?>> GetAll() {
        var res = bookingService.GetAllBookings();
        return ResponseEntity.ok(res);
    }

    // needs to be logged in i.e authorization header ma token needs to be present
    @GetMapping("by-username")
    public ResponseEntity<?> GetByUsername(Principal principal) {
        var userId = authUtil.getUserIdFromPrincipal(principal);
        var res = bookingService.GetAllUserBookings(userId);
        return ResponseEntity.ok(res);
    }
}
