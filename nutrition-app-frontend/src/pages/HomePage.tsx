import React, { useEffect, useState } from 'react';
import { Typography, Button, Row, Col, Card, Alert, Spin, List } from 'antd';
import { useNavigate } from 'react-router-dom';
import { apiService } from '../services/api';
import type { User } from '../types/entities';

const { Title, Paragraph } = Typography;

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const [users, setUsers] = useState<User[] | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {

    const fetchUsers = async () => {
      try {
        const response = await apiService.getUsers({ page: 4, size: 1 });

        console.log('[HomePage] Full response:', response);

        if (
            response.body.status === 'OK' &&
            response.body.payload &&
            Array.isArray(response.body.payload.content)
        ) {
          setUsers(response.body.payload.content);
        } else {
          console.error('[HomePage] Errors:', response.body.errors);
          setError('Failed to load users');
        }
      } catch (err) {
        console.error('[HomePage] Exception:', err);
        setError('Unexpected error');
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);


  // Log users when they are updated
  useEffect(() => {
    if (users) {
      console.log('Fetched users:', users);
    }
  }, [users]);

  return (
      <Row justify="center" align="middle" style={{ minHeight: '80vh', padding: '24px' }}>
        <Col xs={24} sm={20} md={16} lg={12}>
          <Card style={{ textAlign: 'center' }}>
            {loading && <Spin />}
            {error && <Alert type="error" message={error} showIcon style={{ marginBottom: 16 }} />}

            <Title>Welcome to Nutrition App</Title>
            <Paragraph>
              Track your meals, monitor your daily nutrients, and get smart food recommendations.
            </Paragraph>

            <Button type="primary" size="large" onClick={() => navigate('/dashboard')}>
              Go to Dashboard
            </Button>

            {users && (
                <List
                    header={<div>Fetched Users</div>}
                    bordered
                    dataSource={users}
                    renderItem={(user) => (
                        <List.Item key={user.email}>{user.username}</List.Item>
                    )}
                    style={{ marginTop: 24 }}
                />
            )}
          </Card>
        </Col>
      </Row>
  );
};

export default HomePage;
