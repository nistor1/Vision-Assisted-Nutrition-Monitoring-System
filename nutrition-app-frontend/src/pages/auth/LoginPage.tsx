import { useState, useEffect } from 'react';
import { Button, Layout, Typography, Form, Input, Card, Alert } from 'antd';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext.tsx';
import type { AuthRequest, AuthData } from '../../types/UserEntities.ts';
import { getRedirectedPath } from '../../utils/url.ts';
import { apiService } from '../../services/api.ts';

const { Content } = Layout;
const { Title, Text } = Typography;

export default function LoginPage() {
  const { login, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

    const handleFinish = async (values: AuthRequest) => {
        setLoading(true);
        setError(null);

        try {
            const authData: AuthData = await apiService.login(values);
            login(authData);
            navigate(getRedirectedPath(location.search));
        }catch (err) {
          setError("Login failed");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (isAuthenticated) {
            navigate(getRedirectedPath(location.search));
        }
    }, [isAuthenticated, location.search, navigate]);

    return (
        <Layout>
            <Content style={{ display: 'flex', justifyContent: 'center', paddingTop: 80 }}>
                <Card style={{ width: '100%', maxWidth: 400 }}>
                    <Title level={3} style={{ textAlign: 'center' }}>Welcome Back</Title>
                    <Text type="secondary" style={{ display: 'block', textAlign: 'center', marginBottom: 24 }}>
                        Sign in to your Nutrition account
                    </Text>

                    {error && <Alert message={error} type="error" showIcon style={{ marginBottom: 16 }} />}

                    <Form layout="vertical" onFinish={handleFinish}>
                        <Form.Item label="Username" name="username" rules={[{ required: true }]}>
                            <Input />
                        </Form.Item>

                        <Form.Item label="Password" name="password" rules={[{ required: true }]}>
                            <Input.Password />
                        </Form.Item>

                        <Form.Item>
                          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                            <Link to="/forgot-password">Forgot password?</Link>
                          </div>
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit" block loading={loading}>
                                Log In
                            </Button>
                        </Form.Item>
                    </Form>
                </Card>
            </Content>
        </Layout>
    );
}
