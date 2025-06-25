import { useState } from 'react';
import {
    Button,
    Layout,
    Typography,
    Form,
    Input,
    Divider,
    Card,
    Alert,
} from 'antd';
import { useNavigate } from 'react-router-dom';
import { apiService } from '../services/api';
import type { RegisterRequest } from '../types/entities';
import { useAuth } from '../context/AuthContext';

const { Content } = Layout;
const { Title, Text } = Typography;

export default function RegisterPage() {
    const navigate = useNavigate();
    const { login } = useAuth();

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleFinish = async (values: RegisterRequest) => {
        setLoading(true);
        setError(null);
        try {
            const authData = await apiService.register(values);
            login(authData);
            navigate('/home');
        } catch (err: any) {
            setError(err?.message || 'Registration failed');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Layout>
            <Content style={{ display: 'flex', justifyContent: 'center', paddingTop: 80,}}>
                <Card style={{ width: '100%', maxWidth: 400 }}>
                    <Title level={3} style={{ textAlign: 'center' }}>
                        Create an account
                    </Title>
                    <Text type="secondary" style={{ display: 'block', textAlign: 'center', marginBottom: 24 }}>
                        Sign up to access Nutrition App
                    </Text>
                    {error && (
                        <Alert message={error} type="error" showIcon style={{ marginBottom: 16 }} />
                    )}

                    <Form layout="vertical" onFinish={handleFinish}>
                        <Form.Item label="Username" name="username" rules={[{ required: true }]}>
                            <Input />
                        </Form.Item>

                        <Form.Item label="Email" name="email" rules={[{ required: true, type: 'email' }]}>
                            <Input />
                        </Form.Item>

                        <Form.Item label="Password" name="password" rules={[{ required: true, min: 6 }]}>
                            <Input.Password />
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit" block loading={loading}>
                                Register
                            </Button>
                        </Form.Item>
                    </Form>

                    <Divider>Already have an account?</Divider>

                    <Button block onClick={() => navigate('/login')}>
                        Go to Login
                    </Button>
                </Card>
            </Content>
        </Layout>
    );
}
