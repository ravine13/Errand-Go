"use client";

import React, { useEffect, useState } from "react";
import {
    Button,
    Input,
    Card,
    CardBody,
    CardHeader,
    Link,
    Text,
} from "@nextui-org/react";
import NavigationBar from "@/components/Navbar";

const API_BASE = process.env.NEXT_PUBLIC_API_BASE || "http://localhost:9123";

type Profile = {
    id?: number;
    firstName?: string;
    lastName?: string;
    phoneNumber?: string;
    profilePictureUrl?: string;
    account?: { id?: number };
};

export default function ProfilePage() {
    const [accountId, setAccountId] = useState<string>(""); // allow string for input convenience
    const [profile, setProfile] = useState<Profile>({});
    const [loading, setLoading] = useState(false);
    const [saving, setSaving] = useState(false);
    const [message, setMessage] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);

    // Try to prefill accountId from localStorage if available
    useEffect(() => {
        const saved = localStorage.getItem("accountId");
        if (saved) setAccountId(saved);
    }, []);

    const fetchProfile = async (acctId: string) => {
        if (!acctId) {
            setError("Please provide an accountId");
            return;
        }
        setLoading(true);
        setError(null);
        setMessage(null);

        try {
            const res = await fetch(`${API_BASE}/api/profile/${acctId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    // if protected endpoints require auth: add `Authorization: Bearer <token>` here
                    // Authorization: `Bearer ${localStorage.getItem("token") || ""}`
                },
            });

            if (!res.ok) {
                const txt = await res.text();
                throw new Error(txt || `Failed to fetch profile (${res.status})`);
            }

            const data: Profile = await res.json();
            setProfile(data);
            setMessage("Profile loaded.");
        } catch (err: any) {
            setProfile({});
            setError(err.message || "Error fetching profile");
        } finally {
            setLoading(false);
        }
    };

    const handleSave = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!accountId) {
            setError("Please provide an accountId before saving.");
            return;
        }

        setSaving(true);
        setError(null);
        setMessage(null);

        try {
            // Build DTO that matches your ProfileDTO shape (server expects fields in request body)
            const body = {
                firstName: profile.firstName || "",
                lastName: profile.lastName || "",
                phoneNumber: profile.phoneNumber || "",
                profilePictureUrl: profile.profilePictureUrl || "",
            };

            const res = await fetch(`${API_BASE}/api/profile/${accountId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    // add Authorization header if your endpoints are protected:
                    // Authorization: `Bearer ${localStorage.getItem("token") || ""}`
                },
                body: JSON.stringify(body),
            });

            if (!res.ok) {
                const txt = await res.text();
                throw new Error(txt || `Failed to save profile (${res.status})`);
            }

            const saved: Profile = await res.json();
            setProfile(saved);
            setMessage("Profile saved successfully.");
            // optionally persist accountId in localStorage for quicker reuse
            localStorage.setItem("accountId", accountId);
        } catch (err: any) {
            setError(err.message || "Error saving profile");
        } finally {
            setSaving(false);
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
            <NavigationBar />
            <div className="flex items-center justify-center min-h-[calc(100vh-80px)] px-4">
                <Card className="w-full max-w-2xl">
                    <CardHeader className="flex flex-col gap-3 pb-6">
                        <h1 className="text-2xl font-bold text-center">My Profile</h1>
                        <p className="text-gray-600 text-center">
                            Create or update your profile information
                        </p>
                    </CardHeader>

                    <CardBody>
                        <form onSubmit={handleSave} className="space-y-4">
                            <div className="flex gap-3">
                                <Input
                                    label="Account ID"
                                    placeholder="Enter your account id"
                                    value={accountId}
                                    onChange={(e) => setAccountId(e.target.value)}
                                    isRequired
                                    className="flex-1"
                                />
                                <Button
                                    type="button"
                                    onClick={() => fetchProfile(accountId)}
                                    isDisabled={loading || !accountId}
                                >
                                    {loading ? "Loading..." : "Load"}
                                </Button>
                            </div>

                            <Input
                                label="First Name"
                                placeholder="John"
                                value={profile.firstName || ""}
                                onChange={(e) =>
                                    setProfile((p) => ({ ...p, firstName: e.target.value }))
                                }
                            />

                            <Input
                                label="Last Name"
                                placeholder="Doe"
                                value={profile.lastName || ""}
                                onChange={(e) =>
                                    setProfile((p) => ({ ...p, lastName: e.target.value }))
                                }
                            />

                            <Input
                                label="Phone Number"
                                placeholder="+254712345678"
                                value={profile.phoneNumber || ""}
                                onChange={(e) =>
                                    setProfile((p) => ({ ...p, phoneNumber: e.target.value }))
                                }
                            />

                            <Input
                                label="Profile Picture URL"
                                placeholder="https://example.com/me.jpg"
                                value={profile.profilePictureUrl || ""}
                                onChange={(e) =>
                                    setProfile((p) => ({ ...p, profilePictureUrl: e.target.value }))
                                }
                            />

                            {error && <Text color="error">{error}</Text>}
                            {message && <Text color="success">{message}</Text>}

                            <div className="flex gap-3">
                                <Button type="submit" color="primary" isDisabled={saving}>
                                    {saving ? "Saving..." : "Save Profile"}
                                </Button>

                                <Button
                                    type="button"
                                    variant="outline"
                                    onClick={() => {
                                        setProfile({});
                                        setMessage(null);
                                        setError(null);
                                    }}
                                >
                                    Clear
                                </Button>
                            </div>
                        </form>

                        <div className="mt-6 text-center">
                            <Text className="text-gray-600">
                                Need to update account details?{" "}
                                <Link href="/settings" color="primary">
                                    Go to settings
                                </Link>
                            </Text>
                        </div>
                    </CardBody>
                </Card>
            </div>
        </div>
    );
}
