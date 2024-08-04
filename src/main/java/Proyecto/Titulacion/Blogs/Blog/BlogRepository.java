package Proyecto.Titulacion.Blogs.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByTitleBlogContaining(String titleBlog, Pageable pageable);
}
