package com.example.samuraitravel.controller;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// 管理者用の民宿詳細ページ
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//検索機能
import org.springframework.web.bind.annotation.RequestParam;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.repository.HouseRepository;

@Controller
@RequestMapping("/admin/houses")
public class AdminHouseController {
	private final HouseRepository houseRepository;
	
	public AdminHouseController(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	@GetMapping
	//public String index(Model model) {
	//public String index(Model model, Pageable pageable) {
	//public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
	
	// 検索機能
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		
		//Page<House> housePage = houseRepository.findAll(pageable);
		//List<House> houses = houseRepository.findAll();
		
		//　検索機能
		Page<House> housePage;
		
		if (keyword != null && !keyword.isEmpty()) {
			housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			housePage = houseRepository.findAll(pageable);
		}
		
		//model.addAttribute("houses", houses);
		model.addAttribute("housePage", housePage);
		
		// 検索機能
		model.addAttribute("keyword", keyword);
		
		return "admin/houses/index";
	}
	
	// 管理者用の民宿詳細ページ
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		House house = houseRepository.getReferenceById(id);
		
		model.addAttribute("house", house);
		
		return "admin/houses/show";
	}
}
