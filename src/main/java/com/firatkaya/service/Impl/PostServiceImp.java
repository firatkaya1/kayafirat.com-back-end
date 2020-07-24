package com.firatkaya.service.Impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.firatkaya.model.Post;
import com.firatkaya.model.PostExceptr;
import com.firatkaya.model.PostExceptrSearch;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.service.PostService;

@Service
public class PostServiceImp implements PostService{
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	/*
	private static final String VT_API_KEY = "6460e0921976d6ba74a81dc6657de249c0bfd85c16a6b90e3c059ed6819ea6a0";
	private static final String VT_API_URL_SCAN = "https://www.virustotal.com/vtapi/v2/file/scan";
	private static final String VT_API_URL_REPORT = "https://www.virustotal.com/vtapi/v2/file/report?";

	private final RestTemplate restTemplate;
	
	
	
	public PostServiceImp(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
	
	*/
	
	@Override
	public Page<PostExceptr> getAllPost(int pageNumber,int pageSize,String sortedBy,String orderBy) {
		Sort sort; 
		if(orderBy.equals("asc")) 
			 sort = Sort.by(sortedBy).ascending();
		 else 
			 sort = Sort.by(sortedBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,sort);
	
		return postRepository.findAllProjectedBy(pageable);
	}

	@Override
	public Post getPost(String postId) {
		return postRepository.findByPostIdOrderByPostTimeAsc(postId);
	}

	@Override
	public Post savePost(Post post) {
		post.setPostId(UUID.randomUUID().toString());
		postRepository.save(post);
		return post;
	}

	@Override
	public Post updatePost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deletePost(String postId) {
		postRepository.deleteById(postId);
		return true;
	}

	public Collection<?> lastPost(int limit,String ordertype){
		if(ordertype.toLowerCase().equals("desc"))
			return postRepository.orderByDesc(limit,PostExceptr.class);
		else if(ordertype.toLowerCase().equals("asc"))
			return postRepository.orderByAsc(limit,PostExceptr.class);
		else  return null;
	}
	
	public Page<PostExceptrSearch> searchPost(String keyword,int pageNumber,int pageSize,String sortedBy,String orderBy){
		Sort sort; 
		if(orderBy.equals("asc")) 
			 sort = Sort.by(sortedBy).ascending();
		 else 
			 sort = Sort.by(sortedBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,sort);
		
		return postRepository.searchByTitleHeaderTag(pageable,keyword);
	}

	@Override
	public Post getByPostTitle(String postTitle) {
		return postRepository.findByPostTitle(postTitle);
	}

	@Override
	public Collection<?> getByPostTag(String postTag) {
		return postRepository.findByAllPostTag(postTag, PostExceptr.class);
	}
	
	/*
	 * Burayla sonra ilgilenecegim. Güvenlik Açığı
	 * 
	 */
 /*
	@Override
	public boolean checkImage(MultipartFile file) throws IOException {
		MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
		body.add("file", file.getBytes());
		body.add("apikey", VT_API_KEY);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(VT_API_URL_SCAN,requestEntity,String.class);

		String scanId = new Gson().fromJson(response.getBody(), JsonObject.class).getAsJsonObject().getAsJsonPrimitive("scan_id").toString();
		System.out.println("Başlama Saati :"+new Date());
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		            	checkScanId(scanId);
		            	System.out.println("Bitiş Saati :"+new Date());
		            	
		            }
		        },60000 ); // 10 minutes
		       
		return false;
	}
	
	public boolean checkScanId(String scanId) {
		String URL = VT_API_URL_REPORT+"apikey="+VT_API_KEY+"&resource="+scanId.substring(1,scanId.length() - 1);
		String response = restTemplate.getForObject(URL, String.class).toString();
		int resultCode = Integer.parseInt(new Gson().fromJson(response, JsonObject.class).getAsJsonObject().getAsJsonPrimitive("response_code").toString());
		int positive = Integer.parseInt(new Gson().fromJson(response, JsonObject.class).getAsJsonObject().getAsJsonPrimitive("positives").toString());
		System.out.println("Sonuç Saati :"+new Date());
		System.out.println("response :"+response);
		if(resultCode == 0 && positive == 0) {
			System.out.println("response :"+response);
			return true;
		} 
		return false;
		
	}
	*/
	
	
	
}
