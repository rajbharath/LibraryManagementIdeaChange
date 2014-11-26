package main.repository;

import main.model.Author;
import main.model.Book;
import main.model.BookStatus;
import main.model.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepo {
    private Connection connection;
    private PublisherRepo publisherRepo;
    private AuthorRepo authorRepo;

    public BookRepo(BaseDataSource dataSource, PublisherRepo publisherRepo, AuthorRepo authorRepo) throws SQLException, ClassNotFoundException {
        connection = dataSource.getConnection();
        this.publisherRepo = publisherRepo;
        this.authorRepo = authorRepo;
    }

    public Book save(Book book) throws SQLException {

        Integer[] authorIds = populateAuthorIds(book.getAuthors());
        int publisherId = populatePublisherId(book.getPublisher());

        String sql = "insert into book(name,author_ids,publisher_id,status) values(?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, book.getName());
        statement.setArray(2, connection.createArrayOf("int", authorIds));
        statement.setInt(3, publisherId);
        statement.setString(4, book.getBookStatus().toString());
        int insertedId = statement.executeUpdate();
        book.setId(insertedId);
        return book;
    }

    public boolean update(Book book) throws SQLException {
        Integer[] authorIds = populateAuthorIds(book.getAuthors());
        int publisherId = populatePublisherId(book.getPublisher());
        Array authorIdsSql = connection.createArrayOf("int", authorIds);

        String sql = "update book set name=? ,author_ids=? , publisher_id=?,status=? where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, book.getName());
        statement.setArray(2, authorIdsSql);
        statement.setInt(3, publisherId);
        statement.setString(4, book.getBookStatus().toString());
        statement.setInt(5, book.getId());

        return statement.executeUpdate() > 0;
    }

    public boolean delete(Book book) throws SQLException {
        String sql = "delete from book where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, book.getId());
        int returnCode = statement.executeUpdate(sql);
        return returnCode == 1;
    }

    public Book findByName(String bookname) throws SQLException {
        String sql = "select id,name,author_ids,publisher_id,status from book where lower(name)=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, bookname.toLowerCase());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
            return buildBookFromResultSet(resultSet);
        return null;
    }

    public List<Book> findBooksByName(String name) throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "select id,name,author_ids,publisher_id,status from book where lower(name) like'%" + name.toLowerCase() + "%'";

        ResultSet resultSet = statement.executeQuery(sql);
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            books.add(buildBookFromResultSet(resultSet));
        }
        return books;
    }

    private Book buildBookFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Array sqlArray = resultSet.getArray("author_ids");
        Object obj = sqlArray.getArray();
        Integer[] authorIds = (Integer[]) obj;
        int publisherId = resultSet.getInt("publisher_id");
        List<Author> authors = populateAuthors(authorIds);
        Publisher publisher = populatePublisher(publisherId);
        Book book = new Book(name, authors, publisher);
        book.setId(id);
        if (resultSet.getString("status").equalsIgnoreCase("ISSUED")) book.setBookStatus(BookStatus.ISSUED);
        return book;
    }

    private int populatePublisherId(Publisher publisher) throws SQLException {
        int publisherId = publisherRepo.findIdByName(publisher.getName());
        if (publisherId == -1)
            publisherId = publisherRepo.save(publisher.getName());
        return publisherId;
    }

    private Integer[] populateAuthorIds(List<Author> authors) throws SQLException {
        List<String> authorNames = authors.stream().map(a -> a.getName()).collect(Collectors.toList());
        Integer[] authorIds = new Integer[authorNames.size()];
        int i = 0;
        for (String authorName : authorNames) {
            int id = authorRepo.findIdByName(authorName);
            if (id != -1)
                authorIds[i] = id;
            else
                authorIds[i] = authorRepo.save(authorName);
            i++;
        }
        return authorIds;
    }

    private List<Author> populateAuthors(Integer[] authorIds) throws SQLException {
        List<Author> authors = new ArrayList<>();
        for (Integer authorId : authorIds) {
            authors.add(authorRepo.findById(authorId));

        }
        return authors;
    }

    private Publisher populatePublisher(int publisherId) throws SQLException {
        return publisherRepo.findById(publisherId);
    }


}

