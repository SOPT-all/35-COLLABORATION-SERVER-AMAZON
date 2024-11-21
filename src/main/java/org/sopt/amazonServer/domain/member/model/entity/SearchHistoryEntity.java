package org.sopt.amazonServer.domain.member.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.sopt.amazonServer.global.entity.BaseTimeEntity;

@Entity
@Table(
        name = "search_history",
        indexes = {
                @Index(name = "idx_search_history_member_id", columnList = "member_id")
        }
)
public class SearchHistoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private long memberId;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    protected SearchHistoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getKeyword() {
        return keyword;
    }
}
