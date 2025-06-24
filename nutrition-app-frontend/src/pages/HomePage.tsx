import React, { useEffect, useState } from 'react';
import { Typography, Button, Row, Col, Card, Alert, Spin } from 'antd';
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
        const response = await apiService.getUsers();

        if (response.body.status === 200 && response.body.payload) {
          console.log('Users:', response.body.payload);
          setUsers(response.body.payload);
        } else {
          setError('Failed to load users');
          console.error(response.body.errors);
        }
      } catch (err: any) {
        console.error('Unexpected error:', err);
        setError('Unexpected error. Backend might be down.');
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  return (
    <Row justify="center" align="middle" style={{ minHeight: '80vh', padding: '24px' }}>
      <Col xs={24} sm={20} md={16} lg={12}>
        <Card bordered={false} style={{ textAlign: 'center' }}>
          {loading && <Spin />}
          {error && <Alert type="error" message={error} showIcon style={{ marginBottom: 16 }} />}
          <Title>Welcome to Nutrition App</Title>
          <Paragraph>
            Track your meals, monitor your daily nutrients, and get smart food recommendations.
          </Paragraph>
          <Button type="primary" size="large" onClick={() => navigate('/dashboard')}>
            Go to Dashboard
          </Button>
        </Card>
      </Col>
    </Row>
  );
};

export default HomePage;
